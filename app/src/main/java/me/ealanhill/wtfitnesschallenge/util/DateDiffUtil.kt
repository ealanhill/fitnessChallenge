package me.ealanhill.wtfitnesschallenge.util

import android.support.v7.util.DiffUtil
import android.util.Log
import me.ealanhill.wtfitnesschallenge.DateItem

class DateDiffUtil(val dates: List<DateItem>, val newDates: List<DateItem>): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val datesAreSame = dates[oldItemPosition].date == newDates[newItemPosition].date
        Log.d("DateDiffUtil", "Are the dates the same? " + datesAreSame)
        return datesAreSame
    }

    override fun getOldListSize(): Int {
        Log.d("DateDiffUtil", "Date size: " + dates.size)
        return dates.size
    }

    override fun getNewListSize(): Int {
        Log.d("DateDiffUtil", "New dates size: " + newDates.size)
        return newDates.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return dates[oldItemPosition].pointsMap.all { entry: Map.Entry<String, Int> ->
            Log.d("DateDiffUtil", "Old Entry- " + entry.key + ": " + entry.value)
            Log.d("DateDiffUtil", "New Entry- " + newDates[newItemPosition].pointsMap[entry.key])
            newDates[newItemPosition].pointsMap[entry.key] == entry.value
        }
    }
}
