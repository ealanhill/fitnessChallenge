package me.ealanhill.wtfitnesschallenge

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import me.ealanhill.wtfitnesschallenge.action.InitializeCalendarAction
import me.ealanhill.wtfitnesschallenge.databinding.ActivityCalendarBinding
import me.ealanhill.wtfitnesschallenge.pointsEntry.EntryFormItem
import me.ealanhill.wtfitnesschallenge.pointsEntry.PointsDialogFragment
import me.ealanhill.wtfitnesschallenge.state.CalendarState
import me.ealanhill.wtfitnesschallenge.store.MainStore
import okio.Okio
import java.io.InputStream

class CalendarActivity : AppCompatActivity(), LifecycleRegistryOwner, CalendarAdapter.CalendarOnClickListener {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var store: MainStore
    private lateinit var items: List<EntryFormItem>

    private val registry = LifecycleRegistry(this)

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

        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        val type = Types.newParameterizedType(List::class.java, EntryFormItem::class.java)
        val entryFormItemAdapter = moshi.adapter<List<EntryFormItem>>(type)
        assets.open("test.json").use {
            inputStream: InputStream? ->
            items = entryFormItemAdapter.fromJson(Okio.buffer(Okio.source(inputStream)))!!
        }
    }

    override fun onClick(dateItem: DateItem) {

        PointsDialogFragment.newInstance(dateItem.date, items)
                .show(supportFragmentManager, "dialog")
    }
}
