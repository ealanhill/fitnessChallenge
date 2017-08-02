package me.ealanhill.wtfitnesschallenge.di

import dagger.Component
import me.ealanhill.wtfitnesschallenge.pointsEntry.PointsDialogFragment
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        LoadActionCreatorModule::class
))
interface LoadActionCreatorComponent {
    fun inject(pointEntryDialogFragment: PointsDialogFragment)
}