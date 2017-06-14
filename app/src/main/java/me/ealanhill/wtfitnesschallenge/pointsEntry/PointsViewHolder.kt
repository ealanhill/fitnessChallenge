package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class PointsViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item :EntryFormItem)
}
