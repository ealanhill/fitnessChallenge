package me.ealanhill.wtfitnesschallenge.team.actions

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import me.ealanhill.wtfitnesschallenge.Action
import me.ealanhill.wtfitnesschallenge.database.DatabaseTables
import me.tatarka.redux.Dispatcher
import me.tatarka.redux.Thunk
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SuperlativeActionCreator @Inject constructor(private val user: FirebaseUser) {
    private val TAG = "SuperlativeActionCreat"

    fun getSuperlatives(): Thunk<Action, Action> {
        return Thunk { dispatcher ->
            val database = FirebaseDatabase.getInstance().getReference(DatabaseTables.USERS)
            // retrieve the users team
            database.child(user.uid)
                    .child("team")
                    .addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e(TAG, databaseError.message, databaseError.toException())
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            getUsersTeam(dispatcher, dataSnapshot.value as String)
                        }

                    })
        }
    }

    private fun getUsersTeam(dispatcher: Dispatcher<Action, Action>, team: String) {
        val database = FirebaseDatabase.getInstance().getReference(DatabaseTables.TEAMS)
        // get the team name
        database.child(team)
                .child(DatabaseTables.SUPERLATIVES)
                .addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, databaseError.message, databaseError.toException())
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val map = dataSnapshot?.getValue(object: GenericTypeIndicator<Map<@JvmSuppressWildcards String, @JvmSuppressWildcards String>>() {})
                        map?.forEach { key, value ->
                            getTeamMemberNames(dispatcher, key, value)
                        }
                    }

                })
    }

    private fun getTeamMemberNames(dispatcher: Dispatcher<Action, Action>, title: String, memberId: String) {
        val database = FirebaseDatabase.getInstance().getReference(DatabaseTables.USERS)
        // get the names of all the team members
        database.child(memberId)
                .child(DatabaseTables.NAME)
                .addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, databaseError.message, databaseError.toException())
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dispatcher.dispatch(SuperlativeAction(title, dataSnapshot.value as String))
                    }

                })
    }
}