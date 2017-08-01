package me.ealanhill.wtfitnesschallenge

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import me.ealanhill.wtfitnesschallenge.action.InitializeCalendarAction
import me.ealanhill.wtfitnesschallenge.action.LoadActionCreator
import me.ealanhill.wtfitnesschallenge.databinding.ActivityCalendarBinding
import me.ealanhill.wtfitnesschallenge.pointsEntry.PointsDialogFragment
import me.ealanhill.wtfitnesschallenge.state.CalendarState
import me.ealanhill.wtfitnesschallenge.store.MainStore
import java.util.*

class CalendarActivity : AppCompatActivity(), LifecycleRegistryOwner, CalendarAdapter.CalendarOnClickListener {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var store: MainStore

    private val registry = LifecycleRegistry(this)
    private val TAG = "CalendarAcivity"

    override fun getLifecycle(): LifecycleRegistry = registry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayoutManager = LinearLayoutManager(this)

        binding = DataBindingUtil.setContentView<ActivityCalendarBinding>(this, R.layout.activity_calendar)
                .apply {
                    calendarRecyclerView.setHasFixedSize(true)
                    calendarRecyclerView.layoutManager = linearLayoutManager
                    calendarRecyclerView.adapter = CalendarAdapter(this@CalendarActivity)
                }

        val calendarViewModel: CalendarViewModel = ViewModelProviders.of(this).get(CalendarViewModel::class.java)
        store = calendarViewModel.store

        if (savedInstanceState == null) {
            store.dispatch(InitializeCalendarAction)
        }

        store.dispatch(LoadActionCreator().initializeMonth())

        calendarViewModel.state.observe(this, Observer<CalendarState> {
            data ->
            data?.let {
                (binding.calendarRecyclerView.adapter as CalendarAdapter).setState(data.dateItems)
            }
        })
    }

    override fun onClick(dateItem: DateItem) {
        PointsDialogFragment.newInstance(dateItem.date)
                .show(supportFragmentManager, "dialog")
    }
}
