package me.ealanhill.fitnesschallenge.standings

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.ealanhill.fitnesschallenge.MainActivity
import me.ealanhill.fitnesschallenge.R
import me.ealanhill.fitnesschallenge.databinding.FragmentStandingsBinding
import javax.inject.Inject

class StandingsFragment: LifecycleFragment() {
    private lateinit var standingsViewModel: StandingsViewModel
    private lateinit var store: StandingsStore
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: FragmentStandingsBinding

    @Inject
    lateinit var actionCreator: StandingsActionCreator

    companion object {
        fun newInstance(): StandingsFragment {
            return StandingsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        MainActivity.loadActionCreatorComponent.inject(this)

        standingsViewModel = ViewModelProviders.of(this@StandingsFragment).get(StandingsViewModel::class.java)
        store = standingsViewModel.store

        linearLayoutManager = LinearLayoutManager(context)

        binding = DataBindingUtil.inflate<FragmentStandingsBinding>(inflater, R.layout.fragment_standings, container, false)
                .apply {
                    standingsRecyclerView.layoutManager = linearLayoutManager
                    standingsRecyclerView.adapter = StandingsAdapter()
                }

        if (savedInstanceState == null) {
            store.dispatch(actionCreator.getTeamStandings())
        }

        standingsViewModel.state.observe(this, Observer<StandingsState> { data ->
            data?.let {
                (binding.standingsRecyclerView.adapter as StandingsAdapter).setNewTeams(data.teams)
            }

        })

        return binding.root
    }
}
