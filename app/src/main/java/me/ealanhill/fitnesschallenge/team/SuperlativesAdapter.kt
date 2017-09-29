package me.ealanhill.fitnesschallenge.team

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import me.ealanhill.fitnesschallenge.R
import me.ealanhill.fitnesschallenge.team.actions.SuperlativeModel

class SuperlativesAdapter: RecyclerView.Adapter<SuperlativesViewHolder>() {
    var superlatives: List<SuperlativeModel> = emptyList()

    override fun onBindViewHolder(holder: SuperlativesViewHolder, position: Int) {
        superlatives[position].apply {
            holder.bind(title, value)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperlativesViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_text_and_number, parent, false)
        return SuperlativesViewHolder(view as LinearLayout)
    }

    override fun getItemCount(): Int = superlatives.size

    fun setSuperlativeList(newSuperlatives: List<SuperlativeModel>) {
        if (superlatives != newSuperlatives) {
            superlatives = newSuperlatives
            notifyDataSetChanged()
        }
    }
}