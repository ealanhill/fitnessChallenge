package me.ealanhill.wtfitnesschallenge.team

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import me.ealanhill.wtfitnesschallenge.R

class TeamAdapter: RecyclerView.Adapter<TeamMemberViewHolder>() {
    var members: List<TeamMemberModel> = emptyList()

    override fun onBindViewHolder(holder: TeamMemberViewHolder, position: Int) {
        holder.bind(members[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamMemberViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_text_and_number, parent, false)
        return TeamMemberViewHolder(view as LinearLayout)
    }

    override fun getItemCount(): Int = members.size

    fun setTeamMembers(newMembers: List<TeamMemberModel>) {
        if (members != newMembers) {
            members = newMembers
            notifyDataSetChanged()
        }
    }
}