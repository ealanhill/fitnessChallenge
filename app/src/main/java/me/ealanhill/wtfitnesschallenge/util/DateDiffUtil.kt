package me.ealanhill.wtfitnesschallenge.util

import android.support.v7.util.DiffUtil
import me.ealanhill.wtfitnesschallenge.DateItem

class DateDiffUtil(val dates: List<DateItem>, val newDates: List<DateItem>): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return dates[oldItemPosition].date == newDates[newItemPosition].date
    }

    override fun getOldListSize(): Int = dates.size

    override fun getNewListSize(): Int = newDates.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return dates[oldItemPosition].pointsMap.all { entry: Map.Entry<String, Int> ->
            newDates[newItemPosition].pointsMap[entry.key] == entry.value
        }
    }
}
