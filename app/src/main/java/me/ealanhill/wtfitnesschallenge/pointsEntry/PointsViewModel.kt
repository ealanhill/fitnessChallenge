package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.app.Application
import me.ealanhill.wtfitnesschallenge.state.PointsState
import me.ealanhill.wtfitnesschallenge.store.PointStore
import me.tatarka.redux.android.lifecycle.StoreAndroidViewModel

class PointsViewModel(application: Application): StoreAndroidViewModel<PointsState, PointStore>(application, PointStore())
