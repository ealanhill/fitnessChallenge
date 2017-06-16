package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.support.v7.widget.RecyclerView
import android.view.View
import me.ealanhill.wtfitnesschallenge.store.PointStore

abstract class PointsViewHolder(val itemView: View, val pointStore: PointStore): RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item :EntryFormItem)
}
