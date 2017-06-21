package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.app.Application
import me.ealanhill.wtfitnesschallenge.state.PointEntryState
import me.ealanhill.wtfitnesschallenge.store.PointEntryStore
import me.tatarka.redux.android.lifecycle.StoreAndroidViewModel

class PointEntryViewModel(application: Application): StoreAndroidViewModel<PointEntryState, PointEntryStore>(application, PointEntryStore())
