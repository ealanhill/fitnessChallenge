package me.ealanhill.fitnesschallenge.standings

import android.app.Application
import me.tatarka.redux.android.lifecycle.StoreAndroidViewModel

class StandingsViewModel(application: Application): StoreAndroidViewModel<StandingsState, StandingsStore>(application, StandingsStore())
