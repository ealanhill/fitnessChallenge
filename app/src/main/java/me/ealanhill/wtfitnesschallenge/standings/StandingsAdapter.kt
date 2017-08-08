package me.ealanhill.wtfitnesschallenge.standings

import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import me.ealanhill.wtfitnesschallenge.R

class StandingsAdapter: Adapter<StandingsViewHolder>() {
    var teams: Map<String, Int> = emptyMap()

    override fun onBindViewHolder(holder: StandingsViewHolder, position: Int) {
        val team = teams.toList()[position]
        holder.bind(team.first, team.second)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_text_and_number, parent, false)
        return StandingsViewHolder(view as LinearLayout)
    }

    override fun getItemCount(): Int = teams.size

    fun setNewTeams(newTeams: Map<String, Int>) {
        if (teams != newTeams) {
            teams = newTeams
            notifyDataSetChanged()
        }
    }
}