package me.ealanhill.wtfitnesschallenge.calendar.pointsEntry

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class PointsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    abstract fun bind(model: EntryFormModel)
}
