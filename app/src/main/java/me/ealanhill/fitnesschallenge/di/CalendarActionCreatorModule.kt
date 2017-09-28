package me.ealanhill.wtfitnesschallenge.di

import dagger.Module
import dagger.Provides
import me.ealanhill.wtfitnesschallenge.calendar.action.CalendarActionCreator
import javax.inject.Singleton

@Module
class CalendarActionCreatorModule(val calendarActionCreator: CalendarActionCreator) {

    @Provides
    @Singleton
    fun provideCalendarActionCreator(): CalendarActionCreator = calendarActionCreator
}