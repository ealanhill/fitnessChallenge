package me.ealanhill.wtfitnesschallenge.calendar.action

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import me.ealanhill.wtfitnesschallenge.Action
import me.ealanhill.wtfitnesschallenge.calendar.DateItem
import me.ealanhill.wtfitnesschallenge.database.DatabaseTables
import me.ealanhill.wtfitnesschallenge.calendar.pointsEntry.EntryFormModel
import me.tatarka.redux.Dispatcher
import me.tatarka.redux.Thunk
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarActionCreator @Inject constructor(private val user: FirebaseUser) {

    private val tag = "LoadActionCreator"

    fun initializeMonth(): Thunk<Action, Action> {
        return Thunk { dispatcher ->
            val database = FirebaseDatabase.getInstance().reference
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)
            database.child(DatabaseTables.ENTRIES)
                    .child(user.uid)
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
                            updateCalendar(dataSnapshot)
                        }

                        override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                            // TODO: this gets called when the app is loaded
                            Log.i(tag, "onChildAdded")
                            updateCalendar(dataSnapshot)
                        }

                        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                            Log.i(tag, "onChildRemoved")
                        }

                        private fun updateCalendar(dataSnapshot: DataSnapshot) {
                            val map = dataSnapshot.getValue(object: GenericTypeIndicator<Map<@JvmSuppressWildcards String, @JvmSuppressWildcards Int>>() {})
                            map?.apply {
                                dispatcher.dispatch(UpdateCalendarPointsAction.create(DateItem(year, month, dataSnapshot.key.toInt(), 0, map), map))
                            }
                        }

                    })
        }
    }

    fun getEntryForm(year: Int, month: String, day: Int): Thunk<Action, Action> {
        return Thunk { dispatcher ->
            val database = FirebaseDatabase.getInstance().reference
            val items = ArrayList<EntryFormModel>(0)
            database.child(DatabaseTables.POINT_ENTRY_FORM).addListenerForSingleValueEvent(object: ValueEventListener {
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
        database.child(DatabaseTables.ENTRIES)
                .child(user.uid)
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
