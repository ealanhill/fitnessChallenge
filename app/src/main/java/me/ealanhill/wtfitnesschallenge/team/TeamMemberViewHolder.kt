package me.ealanhill.wtfitnesschallenge.team

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import me.ealanhill.wtfitnesschallenge.databinding.ItemTextAndNumberBinding

class TeamMemberViewHolder(itemView: LinearLayout): RecyclerView.ViewHolder(itemView) {
    var binding: ItemTextAndNumberBinding = DataBindingUtil.bind<ItemTextAndNumberBinding>(itemView)

    fun bind(teamMember: TeamMemberModel) {
        binding.itemText.text = teamMember.name
        binding.itemNumber.text = teamMember.points.toString()
    }
}