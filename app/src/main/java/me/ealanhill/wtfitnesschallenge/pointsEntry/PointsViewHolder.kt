package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.support.v7.widget.RecyclerView
import android.view.View
import me.ealanhill.wtfitnesschallenge.model.EntryFormModel

abstract class PointsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    abstract fun bind(model: EntryFormModel)
}
