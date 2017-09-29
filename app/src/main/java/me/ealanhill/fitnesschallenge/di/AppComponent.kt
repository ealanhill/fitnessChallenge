package me.ealanhill.fitnesschallenge.di

import dagger.Component
import me.ealanhill.fitnesschallenge.calendar.CalendarFragment
import me.ealanhill.fitnesschallenge.calendar.pointsEntry.PointsDialogFragment
import me.ealanhill.fitnesschallenge.calendar.pointsEntry.PointEntryReducers
import me.ealanhill.fitnesschallenge.standings.StandingsFragment
import me.ealanhill.fitnesschallenge.team.TeamFragment
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        CalendarActionCreatorModule::class,
        TeamActionCreatorModule::class,
        SuperlativeActionCreatorModule::class,
        StandingsActionCreatorModule::class,
        UserModule::class
))
interface AppComponent {
    fun inject(pointEntryDialogFragment: PointsDialogFragment)

    fun inject(pointEntryReducers: PointEntryReducers)

    fun inject(calendarFragment: CalendarFragment)

    fun inject(teamFragment: TeamFragment)

    fun inject(standingsFragment: StandingsFragment)
}