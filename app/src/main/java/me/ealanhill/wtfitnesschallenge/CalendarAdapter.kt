package me.ealanhill.wtfitnesschallenge

import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import me.ealanhill.wtfitnesschallenge.util.DateDiffUtil
import kotlin.properties.Delegates

class CalendarAdapter(val onClickListener: CalendarOnClickListener) : Adapter<CalendarItemViewHolder>(), AutoUpdatableAdapter {

    var dates: List<DateItem> by Delegates.observable(emptyList()) {
        _, oldList, newList ->
            autoNotify(oldList, newList, DateDiffUtil(oldList, newList)) {
                o, n -> o.date == n.date
            }
    }

    interface CalendarOnClickListener {
        fun onClick(dateItem: DateItem)
    }

    override fun onBindViewHolder(holder: CalendarItemViewHolder, position: Int) {
        holder.bind(dates[position])
        holder.binding.dateItemLayout.setOnClickListener { _ -> onClickListener.onClick(dates[position]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarItemViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.date_item, parent, false)
        return CalendarItemViewHolder(view as LinearLayout)
    }

    override fun getItemCount() = dates.size

    fun setState(newDates: List<DateItem>) {
        dates = newDates
    }
}