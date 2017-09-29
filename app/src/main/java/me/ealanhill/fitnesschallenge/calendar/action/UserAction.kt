package me.ealanhill.fitnesschallenge.calendar.action

import com.google.firebase.auth.FirebaseUser
import me.ealanhill.fitnesschallenge.Action

data class UserAction(val user: FirebaseUser): Action