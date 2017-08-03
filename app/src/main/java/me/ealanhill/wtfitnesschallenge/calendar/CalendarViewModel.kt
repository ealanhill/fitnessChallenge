package me.ealanhill.wtfitnesschallenge.calendar

import android.app.Application
import me.ealanhill.wtfitnesschallenge.state.CalendarState
import me.ealanhill.wtfitnesschallenge.store.MainStore
import me.tatarka.redux.android.lifecycle.StoreAndroidViewModel

class CalendarViewModel(application: Application): StoreAndroidViewModel<CalendarState, MainStore>(application, MainStore())