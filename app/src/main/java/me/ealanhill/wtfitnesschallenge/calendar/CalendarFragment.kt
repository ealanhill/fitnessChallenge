package me.ealanhill.wtfitnesschallenge.calendar

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseUser
import me.ealanhill.wtfitnesschallenge.*
import me.ealanhill.wtfitnesschallenge.action.InitializeCalendarAction
import me.ealanhill.wtfitnesschallenge.action.LoadActionCreator
import me.ealanhill.wtfitnesschallenge.action.UserAction
import me.ealanhill.wtfitnesschallenge.databinding.FragmentCalendarBinding
import me.ealanhill.wtfitnesschallenge.calendar.pointsEntry.PointsDialogFragment
import javax.inject.Inject

class CalendarFragment: LifecycleFragment(), CalendarAdapter.CalendarOnClickListener {

    private val DIALOG_FRAGMENT = 1

    private lateinit var binding: FragmentCalendarBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var calendarViewModel: CalendarViewModel
    private lateinit var store: CalendarStore

    @Inject
    lateinit var loadActionCreator: LoadActionCreator
    @Inject
    lateinit var user: FirebaseUser

    companion object {
        fun newInstance(): CalendarFragment {
            return CalendarFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        MainActivity.loadActionCreatorComponent.inject(this)

        calendarViewModel = ViewModelProviders.of(this@CalendarFragment).get(CalendarViewModel::class.java)
        store = calendarViewModel.store
        store.dispatch(UserAction(user))

        linearLayoutManager = LinearLayoutManager(context)

        binding = DataBindingUtil.inflate<FragmentCalendarBinding>(inflater, R.layout.fragment_calendar, container, false)
                .apply {
                    calendarRecyclerView.setHasFixedSize(true)
                    calendarRecyclerView.layoutManager = linearLayoutManager
                    calendarRecyclerView.adapter = CalendarAdapter(this@CalendarFragment)
                }

        if (savedInstanceState == null) {
            store.dispatch(InitializeCalendarAction)
        }

        store.dispatch(loadActionCreator.initializeMonth())

        calendarViewModel.state.observe(this, Observer<CalendarState> {
            data ->
            data?.let {
                (binding.calendarRecyclerView.adapter as CalendarAdapter).setState(data.dateItems)
            }
        })

        return binding.root
    }

    override fun onClick(dateItem: DateItem) {
        val dialogFragment = PointsDialogFragment.newInstance(dateItem.date)
        dialogFragment.setTargetFragment(this, DIALOG_FRAGMENT)
        dialogFragment.show(fragmentManager, "dialog")
    }
}