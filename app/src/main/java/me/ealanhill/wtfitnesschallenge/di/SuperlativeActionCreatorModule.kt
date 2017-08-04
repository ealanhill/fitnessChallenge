package me.ealanhill.wtfitnesschallenge.di

import dagger.Module
import dagger.Provides
import me.ealanhill.wtfitnesschallenge.team.actions.SuperlativeActionCreator
import javax.inject.Singleton

@Module
class SuperlativeActionCreatorModule(val superlativeActionCreator: SuperlativeActionCreator) {
    @Provides
    @Singleton
    fun provideSuperlativeActionCreator(): SuperlativeActionCreator = superlativeActionCreator
}
