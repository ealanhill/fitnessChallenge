package me.ealanhill.fitnesschallenge.di

import dagger.Module
import dagger.Provides
import me.ealanhill.fitnesschallenge.team.actions.SuperlativeActionCreator
import javax.inject.Singleton

@Module
class SuperlativeActionCreatorModule(val superlativeActionCreator: SuperlativeActionCreator) {
    @Provides
    @Singleton
    fun provideSuperlativeActionCreator(): SuperlativeActionCreator = superlativeActionCreator
}
