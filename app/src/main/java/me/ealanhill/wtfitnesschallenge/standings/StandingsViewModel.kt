package me.ealanhill.wtfitnesschallenge.standings

import android.app.Application
import me.tatarka.redux.android.lifecycle.StoreAndroidViewModel

class StandingsViewModel(application: Application): StoreAndroidViewModel<StandingsState, StandingsStore>(application, StandingsStore())
