package me.ealanhill.fitnesschallenge

import android.app.Application
import android.content.Context

class FitnessChallengeApplication: Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}
