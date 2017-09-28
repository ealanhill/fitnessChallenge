package me.ealanhill.wtfitnesschallenge.di

import com.google.firebase.auth.FirebaseUser
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserModule(val user: FirebaseUser) {

    @Provides
    @Singleton
    fun provideUser(): FirebaseUser = user
}