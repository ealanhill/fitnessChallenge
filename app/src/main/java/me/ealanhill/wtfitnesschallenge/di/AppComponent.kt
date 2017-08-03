package me.ealanhill.wtfitnesschallenge.di

import dagger.Component
import me.ealanhill.wtfitnesschallenge.calendar.CalendarFragment
import me.ealanhill.wtfitnesschallenge.pointsEntry.PointsDialogFragment
import me.ealanhill.wtfitnesschallenge.reducers.PointEntryReducers
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        LoadActionCreatorModule::class,
        UserModule::class
))
interface AppComponent {
    fun inject(pointEntryDialogFragment: PointsDialogFragment)

    fun inject(pointEntryReducers: PointEntryReducers)

    fun inject(calendarFragment: CalendarFragment)
}