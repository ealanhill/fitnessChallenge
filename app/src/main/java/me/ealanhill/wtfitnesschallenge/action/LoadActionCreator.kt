package me.ealanhill.wtfitnesschallenge.action

import android.util.Log
import com.google.firebase.database.*
import me.ealanhill.wtfitnesschallenge.model.EntryFormModel
import me.tatarka.redux.Dispatcher
import me.tatarka.redux.Thunk

class LoadActionCreator {

    private val tag = "LoadActionCreator"

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
                    getUserPoints(dispatcher, month, day, items)
                }
            })
        }
    }

    private fun getUserPoints(dispatcher: Dispatcher<Action, Action>, month: String, day: Int, models: ArrayList<EntryFormModel>) {
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
