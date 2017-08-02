package me.ealanhill.wtfitnesschallenge.reducers

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import me.ealanhill.wtfitnesschallenge.CalendarActivity
import me.ealanhill.wtfitnesschallenge.action.Action
import me.ealanhill.wtfitnesschallenge.action.GetEntryFormItemsAction
import me.ealanhill.wtfitnesschallenge.action.UploadPointsAction
import me.ealanhill.wtfitnesschallenge.model.EntryFormModel
import me.ealanhill.wtfitnesschallenge.state.PointEntryState
import me.tatarka.redux.Reducer
import me.tatarka.redux.Reducers
import javax.inject.Inject

class PointEntryReducers {

    init {
        CalendarActivity.loadActionCreatorComponent.inject(this)
    }

    @Inject
    lateinit var user: FirebaseUser

    fun reducer(): Reducer<Action, PointEntryState> {
        return Reducers.matchClass<Action, PointEntryState>()
                .`when`(GetEntryFormItemsAction::class.java, getEntryFormItems())
                .`when`(UploadPointsAction::class.java, updateFirebaseDatabase())
    }

    fun getEntryFormItems(): Reducer<GetEntryFormItemsAction, PointEntryState> {
        return Reducer { action, state ->
            val items = action.items()
            state.copy(loading = false, entryFormModels = items)
        }
    }

    fun updateFirebaseDatabase(): Reducer<UploadPointsAction, PointEntryState> {
        return Reducer { (models, year, month, day), state ->
            val database = FirebaseDatabase.getInstance()
                    .getReference("entries")
                    .child(user.uid)
                    .child(year.toString())
                    .child(month)
                    .child(Integer.toString(day))
            var total = 0
            models.forEach { entryFormModel: EntryFormModel ->
                database.child(entryFormModel.name).setValue(entryFormModel.value)
                total += entryFormModel.value
            }
            database.child("total")
                    .setValue(total)

            state
        }
    }
}
