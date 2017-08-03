package me.ealanhill.wtfitnesschallenge.di

import dagger.Module
import dagger.Provides
import me.ealanhill.wtfitnesschallenge.calendar.action.LoadActionCreator
import javax.inject.Singleton

@Module
class LoadActionCreatorModule(val loadActionCreator: LoadActionCreator) {

    @Provides
    @Singleton
    fun provideLoadActionCreator(): LoadActionCreator = loadActionCreator
}