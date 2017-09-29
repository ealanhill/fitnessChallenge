package me.ealanhill.fitnesschallenge.standings

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import me.ealanhill.fitnesschallenge.databinding.ItemTextAndNumberBinding

class StandingsViewHolder(itemView: LinearLayout): RecyclerView.ViewHolder(itemView) {
    var binding: ItemTextAndNumberBinding = DataBindingUtil.bind<ItemTextAndNumberBinding>(itemView)

    fun bind(teamName: String, teamPoints: Int) {
        binding.itemText.text = teamName
        binding.itemNumber.text = teamPoints.toString()
    }
}
