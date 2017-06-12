package me.ealanhill.wtfitnesschallenge

import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout

class CalendarAdapter(var dates: List<DateItem>) : Adapter<CalendarItemViewHolder>() {

    override fun onBindViewHolder(holder: CalendarItemViewHolder?, position: Int) {
        holder?.bind(dates[position])
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