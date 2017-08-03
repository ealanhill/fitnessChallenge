package me.ealanhill.wtfitnesschallenge.calendar.pointsEntry

import android.app.Application
import me.tatarka.redux.android.lifecycle.StoreAndroidViewModel

class PointEntryViewModel(application: Application): StoreAndroidViewModel<PointEntryState, PointEntryStore>(application, PointEntryStore())
