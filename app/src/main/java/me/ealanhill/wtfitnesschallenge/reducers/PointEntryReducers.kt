package me.ealanhill.wtfitnesschallenge.reducers

import com.google.firebase.database.FirebaseDatabase
import me.ealanhill.wtfitnesschallenge.action.Action
import me.ealanhill.wtfitnesschallenge.action.GetEntryFormItemsAction
import me.ealanhill.wtfitnesschallenge.action.UploadPointsAction
import me.ealanhill.wtfitnesschallenge.pointsEntry.EntryFormItem
import me.ealanhill.wtfitnesschallenge.state.PointEntryState
import me.tatarka.redux.Reducer
import me.tatarka.redux.Reducers

object PointEntryReducers {

    fun reducer(): Reducer<Action, PointEntryState> {
        return Reducers.matchClass<Action, PointEntryState>()
                .`when`(GetEntryFormItemsAction::class.java, getEntryFormItems())
                .`when`(UploadPointsAction::class.java, updateFirebaseDatabase())
    }

    fun getEntryFormItems(): Reducer<GetEntryFormItemsAction, PointEntryState> {
        return Reducer { action, state ->
            val items = action.items()
            state.copy(loading = false, entryFormItems = items)
        }
    }

    fun updateFirebaseDatabase(): Reducer<UploadPointsAction, PointEntryState> {
        return Reducer { action, state ->
            val database = FirebaseDatabase.getInstance().getReference(action.month).child(Integer.toString(action.day))
            var total = 0
            action.items.forEach { entryFormItem: EntryFormItem ->
                database.child("user 1").child(entryFormItem.name).setValue(entryFormItem.value)
                total += entryFormItem.value
            }
            database.child("user 1").child("total").setValue(total)

            state
        }
    }
}
