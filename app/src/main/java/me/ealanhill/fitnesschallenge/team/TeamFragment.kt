package me.ealanhill.wtfitnesschallenge.team

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.ealanhill.wtfitnesschallenge.MainActivity
import me.ealanhill.wtfitnesschallenge.R
import me.ealanhill.wtfitnesschallenge.databinding.FragmentTeamBinding
import me.ealanhill.wtfitnesschallenge.team.actions.SuperlativeActionCreator
import me.ealanhill.wtfitnesschallenge.team.actions.TeamActionCreator
import javax.inject.Inject

class TeamFragment: LifecycleFragment() {

    private lateinit var teamViewModel: TeamViewModel
    private lateinit var store: TeamStore
    private lateinit var teamLinearLayoutManager: LinearLayoutManager
    private lateinit var superlativesLinearLayoutManager: LinearLayoutManager
    private lateinit var binding: FragmentTeamBinding

    @Inject
    lateinit var teamActionCreator: TeamActionCreator
    @Inject
    lateinit var superlativeActionCreator: SuperlativeActionCreator

    companion object {
        fun newInstance(): TeamFragment {
            return TeamFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        MainActivity.loadActionCreatorComponent.inject(this)

        teamViewModel = ViewModelProviders.of(this@TeamFragment).get(TeamViewModel::class.java)
        store = teamViewModel.store

        teamLinearLayoutManager = LinearLayoutManager(context)
        superlativesLinearLayoutManager = LinearLayoutManager(context)

        binding = DataBindingUtil.inflate<FragmentTeamBinding>(inflater, R.layout.fragment_team, container, false)
                .apply {
                    teamMembers.layoutManager = teamLinearLayoutManager
                    teamMembers.adapter = TeamAdapter()
                    teamSuperlatives.layoutManager = superlativesLinearLayoutManager
                    teamSuperlatives.adapter = SuperlativesAdapter()
                }

        if (savedInstanceState == null) {
            store.dispatch(teamActionCreator.getTeammates())
            store.dispatch(superlativeActionCreator.getSuperlatives())
        }

        teamViewModel.state.observe(this, Observer<TeamState> { data ->
            data?.let {
                (binding.teamMembers.adapter as TeamAdapter).setTeamMembers(data.teamMembers)
                (binding.teamSuperlatives.adapter as SuperlativesAdapter).setSuperlativeList(data.superlatives)
                if (binding.teamName.text != data.teamName) {
                    binding.teamName.text = data.teamName
                }
            }
        })

        return binding.root
    }
}