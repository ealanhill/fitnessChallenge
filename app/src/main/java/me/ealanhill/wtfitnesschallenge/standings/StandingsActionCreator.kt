package me.ealanhill.wtfitnesschallenge.standings

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import me.ealanhill.wtfitnesschallenge.Action
import me.ealanhill.wtfitnesschallenge.database.DatabaseTables
import me.tatarka.redux.Thunk

class StandingsActionCreator {

    fun getTeamStandings(): Thunk<Action, Action> {
        return Thunk { dispatcher ->
            val database = FirebaseDatabase.getInstance().getReference(DatabaseTables.TEAMS)
            database.orderByChild("averagePoints")
                    .addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e("StandingsActionCreator", "Error", databaseError.toException())
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val teams = HashMap<String, Int>()
                            dataSnapshot.children.forEach { data: DataSnapshot ->
                                val points = getValue(data)
                                teams.put(data.child("name").value as String, points)
                            }
                            dispatcher.dispatch(StandingsAction(teams))
                        }
                    })
        }
    }

    fun getValue(dataSnapshot: DataSnapshot): Int =
        if (dataSnapshot.child("averagePoints").value == null)
            0
        else
            (dataSnapshot.child("averagePoints").value as Long).toInt()
}