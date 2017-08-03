package me.ealanhill.wtfitnesschallenge.calendar.action

import com.google.firebase.auth.FirebaseUser
import me.ealanhill.wtfitnesschallenge.Action

data class UserAction(val user: FirebaseUser): Action