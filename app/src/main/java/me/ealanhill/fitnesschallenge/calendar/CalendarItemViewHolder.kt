package me.ealanhill.fitnesschallenge.calendar

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import me.ealanhill.fitnesschallenge.R
import me.ealanhill.fitnesschallenge.databinding.ItemTextAndNumberBinding

class CalendarItemViewHolder(itemView: LinearLayout) : RecyclerView.ViewHolder(itemView) {
    var binding: ItemTextAndNumberBinding = DataBindingUtil.bind<ItemTextAndNumberBinding>(itemView)

    fun bind(dateItem: DateItem) {
        binding.itemText.text = itemView.context.getString(R.string.date_format, dateItem.month, dateItem.date)
        binding.itemNumber.text = dateItem.totalPoints.toString()
    }
}
