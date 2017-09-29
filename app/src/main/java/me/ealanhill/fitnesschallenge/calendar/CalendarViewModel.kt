package me.ealanhill.fitnesschallenge.calendar

import android.app.Application
import me.tatarka.redux.android.lifecycle.StoreAndroidViewModel

class CalendarViewModel(application: Application): StoreAndroidViewModel<CalendarState, CalendarStore>(application, CalendarStore())