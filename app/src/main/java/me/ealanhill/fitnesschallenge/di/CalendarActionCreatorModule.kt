package me.ealanhill.fitnesschallenge.di

import dagger.Module
import dagger.Provides
import me.ealanhill.fitnesschallenge.calendar.action.CalendarActionCreator
import javax.inject.Singleton

@Module
class CalendarActionCreatorModule(val calendarActionCreator: CalendarActionCreator) {

    @Provides
    @Singleton
    fun provideCalendarActionCreator(): CalendarActionCreator = calendarActionCreator
}