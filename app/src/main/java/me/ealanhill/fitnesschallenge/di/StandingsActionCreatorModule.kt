package me.ealanhill.fitnesschallenge.di

import dagger.Module
import dagger.Provides
import me.ealanhill.fitnesschallenge.standings.StandingsActionCreator
import javax.inject.Singleton

@Module
class StandingsActionCreatorModule(val standingsActionCreator: StandingsActionCreator) {
    @Provides
    @Singleton
    fun provideStandingsActionCreator(): StandingsActionCreator = standingsActionCreator
}