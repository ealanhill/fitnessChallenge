package me.ealanhill.wtfitnesschallenge.team.actions

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import me.ealanhill.wtfitnesschallenge.Action
import me.ealanhill.wtfitnesschallenge.database.DatabaseTables
import me.ealanhill.wtfitnesschallenge.team.TeamMemberModel
import me.tatarka.redux.Dispatcher
import me.tatarka.redux.Thunk
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeamActionCreator @Inject constructor(private val user: FirebaseUser) {

    private val TAG = "TeamActionCreator"

    fun getTeammates(): Thunk<Action, Action> {
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
                            if (dataSnapshot.value != null) {
                                getTeamMembers(dispatcher, dataSnapshot.value as String)
                            }
                        }

                    })
        }
    }

    private fun getTeamMembers(dispatcher: Dispatcher<Action, Action>, team: String) {
        val database = FirebaseDatabase.getInstance().getReference(DatabaseTables.TEAMS)
        // get the team name
        database.child(team)
                .child(DatabaseTables.NAME)
                .addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, databaseError.message, databaseError.toException())
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dispatcher.dispatch(UpdateTeamNameAction.create(dataSnapshot.value as String))
                    }

                })
        // get each team member
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

    private fun getTeamMember(dispatcher: Dispatcher<Action, Action>, teamMemberId: String) {
        val database = FirebaseDatabase.getInstance().getReference(DatabaseTables.USERS)
        // get the names of all the team members
        database.child(teamMemberId)
                .child(DatabaseTables.NAME)
                .addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, databaseError.message, databaseError.toException())
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dispatcher.dispatch(AddTeamMemberAction(
                                TeamMemberModel(teamMemberId,dataSnapshot.value as String, 0)))
                        getTeamMembersTotals(dispatcher, teamMemberId)
                    }

                })
    }

    private fun getTeamMembersTotals(dispatcher: Dispatcher<Action, Action>, teamMemberId: String) {
        // total the users points
        val calendar = Calendar.getInstance()
        FirebaseDatabase.getInstance()
                .getReference(DatabaseTables.ENTRIES)
                .child(teamMemberId)
                .child(calendar.get(Calendar.YEAR).toString())
                .child(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US))
                .child("0") // the month total
                .child("total")
                .addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, databaseError.message, databaseError.toException())
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dispatcher.dispatch(UpdateUserMonthTotalAction(teamMemberId,
                                (dataSnapshot.value as Long).toInt()))
                    }

                })
    }
}
