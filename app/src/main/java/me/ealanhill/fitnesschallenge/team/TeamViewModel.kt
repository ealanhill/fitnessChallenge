package me.ealanhill.fitnesschallenge.team

import android.app.Application
import me.tatarka.redux.android.lifecycle.StoreAndroidViewModel

class TeamViewModel(application: Application): StoreAndroidViewModel<TeamState, TeamStore>(application, TeamStore())
