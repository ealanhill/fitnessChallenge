package me.ealanhill.wtfitnesschallenge.di

import dagger.Module
import dagger.Provides
import me.ealanhill.wtfitnesschallenge.standings.StandingsActionCreator
import javax.inject.Singleton

@Module
class StandingsActionCreatorModule(val standingsActionCreator: StandingsActionCreator) {
    @Provides
    @Singleton
    fun provideStandingsActionCreator(): StandingsActionCreator = standingsActionCreator
}