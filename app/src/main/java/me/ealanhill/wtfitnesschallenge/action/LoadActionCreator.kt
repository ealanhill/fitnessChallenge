package me.ealanhill.wtfitnesschallenge.action

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import me.ealanhill.wtfitnesschallenge.DateItem
import me.ealanhill.wtfitnesschallenge.model.EntryFormModel
import me.tatarka.redux.Dispatcher
import me.tatarka.redux.Thunk
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadActionCreator @Inject constructor(private val user: FirebaseUser) {

    private val tag = "LoadActionCreator"

    fun initializeMonth(): Thunk<Action, Action> {
        return Thunk { dispatcher ->
            val database = FirebaseDatabase.getInstance().reference
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)
            database.child("entries")
                    .child("EsSFN71XaTPB9iWis3pPXAsJemG2")
                    .child(year.toString())
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
                                dispatcher.dispatch(UpdateCalendarPointsAction.create(DateItem(year, month, dataSnapshot.key.toInt(), 0, map), map))
                            }
                        }

                        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                            Log.i(tag, "onChildRemoved")
                        }

                    })
        }
    }

    fun getEntryForm(year: Int, month: String, day: Int): Thunk<Action, Action> {
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
                    getEntryFormUserPoints(dispatcher,year, month, day, items)
                }
            })
        }
    }

    private fun getEntryFormUserPoints(dispatcher: Dispatcher<Action, Action>,
                                       year: Int,
                                       month: String,
                                       day: Int,
                                       models: ArrayList<EntryFormModel>) {
        val database = FirebaseDatabase.getInstance().reference
        database.child("entries")
                .child("EsSFN71XaTPB9iWis3pPXAsJemG2")
                .child(year.toString())
                .child(month)
                .child(day.toString())
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
