package me.ealanhill.wtfitnesschallenge.action

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import me.ealanhill.wtfitnesschallenge.pointsEntry.EntryFormItem
import me.tatarka.redux.Thunk

class LoadActionCreator {

    private val tag = "LoadActionCreator"

    fun getEntryForm(): Thunk<Action, Action> {
        return Thunk { dispatcher ->
            val database = FirebaseDatabase.getInstance().reference
            val items = ArrayList<EntryFormItem>(0)
            database.child("pointEntryForm").addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError?) {
                    databaseError?.let {
                        Log.e(tag, toString())
                    }
                }

                override fun onDataChange(databaseSnapshot: DataSnapshot?) {
                    databaseSnapshot?.children?.forEach { child: DataSnapshot? ->
                        items.add(child?.getValue(EntryFormItem::class.java)!!)
                    }
                    dispatcher.dispatch(GetEntryFormItemsAction.create(items))
                }
            })
        }
    }
}
