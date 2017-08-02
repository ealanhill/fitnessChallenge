package me.ealanhill.wtfitnesschallenge

import android.app.Activity
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import me.ealanhill.wtfitnesschallenge.action.InitializeCalendarAction
import me.ealanhill.wtfitnesschallenge.action.LoadActionCreator
import me.ealanhill.wtfitnesschallenge.action.UserAction
import me.ealanhill.wtfitnesschallenge.databinding.ActivityCalendarBinding
import me.ealanhill.wtfitnesschallenge.di.*
import me.ealanhill.wtfitnesschallenge.pointsEntry.PointsDialogFragment
import me.ealanhill.wtfitnesschallenge.state.CalendarState
import me.ealanhill.wtfitnesschallenge.store.MainStore
import java.util.*
import javax.inject.Inject

class CalendarActivity : AppCompatActivity(), LifecycleRegistryOwner, CalendarAdapter.CalendarOnClickListener {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var store: MainStore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private lateinit var loadActionCreator: LoadActionCreator
    private lateinit var calendarViewModel: CalendarViewModel
    private lateinit var drawerToggle: ActionBarDrawerToggle

    private val registry = LifecycleRegistry(this)
    private val SIGN_IN = 1
    private val TAG = "CalendarAcivity"

    companion object {
        lateinit var loadActionCreatorComponent: LoadActionCreatorComponent
    }

    override fun getLifecycle(): LifecycleRegistry = registry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                loadActionCreator = LoadActionCreator(user)
                loadActionCreatorComponent = DaggerLoadActionCreatorComponent.builder()
                        .loadActionCreatorModule(LoadActionCreatorModule(loadActionCreator))
                        .userModule(UserModule(user))
                        .build()

                calendarViewModel = ViewModelProviders.of(this).get(CalendarViewModel::class.java)
                store = calendarViewModel.store
                store.dispatch(UserAction(user))

                initializeAfterSignIn(savedInstanceState)
            } else {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(mutableListOf(
                                        AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                                )).build(),
                        SIGN_IN
                )
            }
        }
    }

    private fun initializeAfterSignIn(savedInstanceState: Bundle?) {
        linearLayoutManager = LinearLayoutManager(this)

        binding = DataBindingUtil.setContentView<ActivityCalendarBinding>(this, R.layout.activity_calendar)
                .apply {
                    calendarRecyclerView.setHasFixedSize(true)
                    calendarRecyclerView.layoutManager = linearLayoutManager
                    calendarRecyclerView.adapter = CalendarAdapter(this@CalendarActivity)
                    setSupportActionBar(toolbar)
                    if (supportActionBar != null) {
                        (supportActionBar as ActionBar).setDisplayHomeAsUpEnabled(true)
                        drawerToggle = ActionBarDrawerToggle(this@CalendarActivity,
                                drawer, toolbar, R.string.drawer_open, R.string.drawer_close)
                        drawerToggle.isDrawerIndicatorEnabled = true
                        drawerToggle.syncState()
                    }
                    drawer.addDrawerListener(drawerToggle)
                }

        if (savedInstanceState == null) {
            store.dispatch(InitializeCalendarAction)
        }

        store.dispatch(loadActionCreator.initializeMonth())

        calendarViewModel.state.observe(this, Observer<CalendarState> {
            data ->
            data?.let {
                (binding.calendarRecyclerView.adapter as CalendarAdapter).setState(data.dateItems)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN) {
            when (resultCode) {
                Activity.RESULT_OK ->
                    Toast.makeText(this, "Signed In!", Toast.LENGTH_SHORT).show()
                Activity.RESULT_CANCELED ->
                    Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(dateItem: DateItem) {
        PointsDialogFragment.newInstance(dateItem.date)
                .show(supportFragmentManager, "dialog")
    }

    override fun onPause() {
        super.onPause()
        authStateListener?.apply {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }

    override fun onResume() {
        super.onResume()
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }
}
