package me.ealanhill.fitnesschallenge.di

import dagger.Module
import dagger.Provides
import me.ealanhill.fitnesschallenge.team.actions.TeamActionCreator
import javax.inject.Singleton

@Module
class TeamActionCreatorModule(val teamActionCreator: TeamActionCreator) {
    @Provides
    @Singleton
    fun provideTeamActionCreator(): TeamActionCreator = teamActionCreator
}