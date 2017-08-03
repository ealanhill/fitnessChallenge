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
class TeamActionCreator @Inject constructor(private val user: FirebaseUser) {

    private val TAG = "TeamActionCreator"

    fun getTeammates(): Thunk<Action, Action> {
        return Thunk { dispatcher ->
            val database = FirebaseDatabase.getInstance().getReference(DatabaseTables.USERS)
            database.child(user.uid)
                    .child("team")
                    .addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e(TAG, databaseError.message, databaseError.toException())
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            getTeamMembers(dispatcher, dataSnapshot.value as String)
                        }

                    })
        }
    }

    private fun getTeamMembers(dispatcher: Dispatcher<Action, Action>, team: String) {
        val database = FirebaseDatabase.getInstance().getReference(DatabaseTables.TEAMS)
        database.child(team)
                .child(DatabaseTables.MEMBERS)
                .addChildEventListener(object: ChildEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, databaseError.message, databaseError.toException())
                    }

                    override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                        Log.i(TAG, "Child moved, " + previousChildName)
                    }

                    override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                        getTeamMember(dispatcher, dataSnapshot.value as String)
                    }

                    override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })
    }

    private fun getTeamMember(dispatcher: Dispatcher<Action, Action>, teamMember: String) {
        val database = FirebaseDatabase.getInstance().getReference(DatabaseTables.USERS)
        database.child(teamMember)
                .addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, databaseError.message, databaseError.toException())
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dispatcher.dispatch(AddTeamMemberAction.create(dataSnapshot.child("name").value as String, 0))
                    }

                })
    }
}
