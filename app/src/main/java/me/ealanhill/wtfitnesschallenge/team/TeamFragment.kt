package me.ealanhill.wtfitnesschallenge.team

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.ealanhill.wtfitnesschallenge.R
import me.ealanhill.wtfitnesschallenge.databinding.FragmentTeamBinding
import me.ealanhill.wtfitnesschallenge.team.actions.LoadTeamMembersAction

class TeamFragment: LifecycleFragment() {

    private lateinit var teamViewModel: TeamViewModel
    private lateinit var store: TeamStore
    private lateinit var teamLinearLayoutManager: LinearLayoutManager
    private lateinit var superlativesLinearLayoutManager: LinearLayoutManager
    private lateinit var binding: FragmentTeamBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        teamViewModel = ViewModelProviders.of(this@TeamFragment).get(TeamViewModel::class.java)
        store = teamViewModel.store

        teamLinearLayoutManager = LinearLayoutManager(context)
        superlativesLinearLayoutManager = LinearLayoutManager(context)

        binding = DataBindingUtil.inflate<FragmentTeamBinding>(inflater, R.layout.fragment_team, container, false)
                .apply {
                    teamMembers.layoutManager = teamLinearLayoutManager
                    teamSuperlatives.layoutManager = superlativesLinearLayoutManager
                }

        if (savedInstanceState == null) {
            store.dispatch(LoadTeamMembersAction)
        }

        return binding.root
        /*
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
         */
    }
}