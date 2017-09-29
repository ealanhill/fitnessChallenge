package me.ealanhill.fitnesschallenge.calendar.pointsEntry

import android.app.Application
import me.tatarka.redux.android.lifecycle.StoreAndroidViewModel

class PointEntryViewModel(application: Application): StoreAndroidViewModel<PointEntryState, PointEntryStore>(application, PointEntryStore())
