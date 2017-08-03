package me.ealanhill.wtfitnesschallenge.di

import dagger.Component
import me.ealanhill.wtfitnesschallenge.calendar.CalendarFragment
import me.ealanhill.wtfitnesschallenge.calendar.pointsEntry.PointsDialogFragment
import me.ealanhill.wtfitnesschallenge.calendar.pointsEntry.PointEntryReducers
import me.ealanhill.wtfitnesschallenge.team.TeamFragment
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        CalendarActionCreatorModule::class,
        TeamActionCreatorModule::class,
        UserModule::class
))
interface AppComponent {
    fun inject(pointEntryDialogFragment: PointsDialogFragment)

    fun inject(pointEntryReducers: PointEntryReducers)

    fun inject(calendarFragment: CalendarFragment)

    fun inject(teamFragment: TeamFragment)
}