package me.ealanhill.wtfitnesschallenge

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import me.ealanhill.wtfitnesschallenge.databinding.DateItemBinding

class CalendarItemViewHolder(itemView: LinearLayout) : RecyclerView.ViewHolder(itemView) {
    var binding: DateItemBinding = DataBindingUtil.bind<DateItemBinding>(itemView)

    fun bind(dateItem: DateItem) {
        binding.date.text = itemView.context.getString(R.string.date_format, dateItem.month, dateItem.date)
        binding.datePoints.text = dateItem.totalPoints.toString()
    }
}
