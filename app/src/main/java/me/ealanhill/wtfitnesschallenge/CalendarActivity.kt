package me.ealanhill.wtfitnesschallenge

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import me.ealanhill.wtfitnesschallenge.databinding.ActivityCalendarBinding
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayoutManager = LinearLayoutManager(this)

        val currentCalendar: Calendar = Calendar.getInstance()
        currentCalendar.set(currentCalendar[Calendar.YEAR], currentCalendar[Calendar.MONTH], 1)
        val daysInMonth: Int = currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val dates: MutableList<DateItem> = ArrayList(daysInMonth)
        val month: String = currentCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
        for (day: Int in 1..daysInMonth) {
            dates.add(DateItem(
                       month,
                       day,
                       0
                   ))
        }

        binding = DataBindingUtil.setContentView<ActivityCalendarBinding>(this, R.layout.activity_calendar)
                .apply {
                    calendarRecyclerView.setHasFixedSize(true)
                    calendarRecyclerView.layoutManager = linearLayoutManager
                    calendarRecyclerView.adapter = CalendarAdapter(dates)
                }
    }
}
