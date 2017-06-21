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
import com.google.firebase.database.*
import me.ealanhill.wtfitnesschallenge.action.InitializeCalendarAction
import me.ealanhill.wtfitnesschallenge.databinding.ActivityCalendarBinding
import me.ealanhill.wtfitnesschallenge.pointsEntry.EntryFormItem
import me.ealanhill.wtfitnesschallenge.pointsEntry.PointsDialogFragment
import me.ealanhill.wtfitnesschallenge.state.CalendarState
import me.ealanhill.wtfitnesschallenge.store.MainStore

class CalendarActivity : AppCompatActivity(), LifecycleRegistryOwner, CalendarAdapter.CalendarOnClickListener {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var store: MainStore
    private lateinit var database: DatabaseReference

    private val registry = LifecycleRegistry(this)
    private val items: MutableList<EntryFormItem> = ArrayList<EntryFormItem>()
    private val tag: String = "CalendarActivity"

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

        calendarViewModel.state.observe(this, Observer<CalendarState> {
            data ->
            data?.let {
                (binding.calendarRecyclerView.adapter as CalendarAdapter).setState(data.dateItems)
            }
        })

        database = FirebaseDatabase.getInstance().getReference("pointEntryForm")
        database.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                if (databaseError != null) {
                    Log.e(tag, databaseError.toString())
                }
            }

            override fun onDataChange(databaseSnapshot: DataSnapshot?) {
                databaseSnapshot?.children?.forEach { child: DataSnapshot? ->
                    items.add(child?.getValue(EntryFormItem::class.java)!!)
                }
            }

        })
    }

    override fun onClick(dateItem: DateItem) {
        PointsDialogFragment.newInstance(dateItem.date, items)
                .show(supportFragmentManager, "dialog")
    }
}
