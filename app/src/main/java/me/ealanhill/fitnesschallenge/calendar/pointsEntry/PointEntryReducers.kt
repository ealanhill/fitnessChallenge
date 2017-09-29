package me.ealanhill.fitnesschallenge.calendar.pointsEntry

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import me.ealanhill.fitnesschallenge.MainActivity
import me.ealanhill.fitnesschallenge.Action
import me.ealanhill.fitnesschallenge.calendar.action.GetEntryFormItemsAction
import me.ealanhill.fitnesschallenge.calendar.action.UploadPointsAction
import me.ealanhill.fitnesschallenge.database.DatabaseTables
import me.tatarka.redux.Reducer
import me.tatarka.redux.Reducers
import javax.inject.Inject

class PointEntryReducers {

    init {
        MainActivity.loadActionCreatorComponent.inject(this)
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
                    .getReference(DatabaseTables.ENTRIES)
                    .child(user.uid)
                    .child(year.toString())
                    .child(month)
                    .child(Integer.toString(day))
            models.forEach { entryFormModel: EntryFormModel ->
                database.child(entryFormModel.name).setValue(entryFormModel.value)
            }

            state
        }
    }
}
