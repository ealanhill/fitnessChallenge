package me.ealanhill.wtfitnesschallenge.action

import android.util.Log
import com.google.firebase.database.*
import me.ealanhill.wtfitnesschallenge.DateItem
import me.ealanhill.wtfitnesschallenge.model.EntryFormModel
import me.tatarka.redux.Dispatcher
import me.tatarka.redux.Thunk
import java.util.*

class LoadActionCreator {

    private val tag = "LoadActionCreator"

    fun initializeMonth(): Thunk<Action, Action> {
        return Thunk { dispatcher ->
            val database = FirebaseDatabase.getInstance().reference
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR).toString()
            val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)
            database.child("entries")
                    .child("EsSFN71XaTPB9iWis3pPXAsJemG2")
                    .child(year)
                    .child(month)
                    .addChildEventListener(object: ChildEventListener {
                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e(tag, "Error listening to month", databaseError.toException())
                        }

                        override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                            Log.i(tag, "onChildMoved")
                        }

                        override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                            Log.i(tag, "onChildChanged")
                        }

                        override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                            // TODO: this gets called when the app is loaded
                            Log.i(tag, "onChildAdded")
                            val map = dataSnapshot.getValue(object: GenericTypeIndicator<Map<@JvmSuppressWildcards String, @JvmSuppressWildcards Int>>() {})
                            map?.apply {
                                dispatcher.dispatch(UpdateCalendarPointsAction.create(DateItem(month, dataSnapshot.key.toInt(), 0, map), map))
                            }
                        }

                        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                            Log.i(tag, "onChildRemoved")
                        }

                    })
        }
    }

    fun getEntryForm(month: String, day: Int): Thunk<Action, Action> {
        return Thunk { dispatcher ->
            val database = FirebaseDatabase.getInstance().reference
            val items = ArrayList<EntryFormModel>(0)
            database.child("pointEntryForm").addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError?) {
                    databaseError?.let {
                        Log.e(tag, toString())
                    }
                }

                override fun onDataChange(databaseSnapshot: DataSnapshot?) {
                    databaseSnapshot?.children?.forEach { child: DataSnapshot? ->
                        items.add(child?.getValue(EntryFormModel::class.java)!!)
                    }
                    getEntryFormUserPoints(dispatcher, month, day, items)
                }
            })
        }
    }

    private fun getEntryFormUserPoints(dispatcher: Dispatcher<Action, Action>,
                                       month: String,
                                       day: Int,
                                       models: ArrayList<EntryFormModel>) {
        val database = FirebaseDatabase.getInstance().reference
        database.child(month)
                .child(Integer.toString(day))
                .child("user 1")
                .addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(tag, toString())
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot?) {
                        val map = dataSnapshot?.getValue(object: GenericTypeIndicator<Map<@JvmSuppressWildcards String, @JvmSuppressWildcards Int>>() {})
                        models.forEach { entryFormModel: EntryFormModel ->
                            if (map != null) {
                                map[entryFormModel.name]?.let { i -> entryFormModel.value = i }
                            }
                        }
                        dispatcher.dispatch(GetEntryFormItemsAction.create(models))
                    }
                })
    }
}