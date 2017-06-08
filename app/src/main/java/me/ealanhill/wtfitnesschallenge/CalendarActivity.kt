package me.ealanhill.wtfitnesschallenge

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager

import me.ealanhill.wtfitnesschallenge.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {

    private var binding: ActivityCalendarBinding? = null
    private var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityCalendarBinding>(this, R.layout.activity_calendar)
        binding!!.calendarRecyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        binding!!.calendarRecyclerView.layoutManager = linearLayoutManager
    }
}
