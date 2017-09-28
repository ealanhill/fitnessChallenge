package me.ealanhill.wtfitnesschallenge

import android.app.Activity
import android.arch.lifecycle.LifecycleFragment
import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import me.ealanhill.wtfitnesschallenge.calendar.action.CalendarActionCreator
import me.ealanhill.wtfitnesschallenge.calendar.CalendarFragment
import me.ealanhill.wtfitnesschallenge.databinding.ActivityMainBinding
import me.ealanhill.wtfitnesschallenge.di.*
import me.ealanhill.wtfitnesschallenge.standings.StandingsActionCreator
import me.ealanhill.wtfitnesschallenge.standings.StandingsFragment
import me.ealanhill.wtfitnesschallenge.team.TeamFragment
import me.ealanhill.wtfitnesschallenge.team.actions.SuperlativeActionCreator
import me.ealanhill.wtfitnesschallenge.team.actions.TeamActionCreator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private lateinit var drawerToggle: ActionBarDrawerToggle

    private val SIGN_IN = 1
    private val TAG = "CalendarAcivity"

    companion object {
        lateinit var loadActionCreatorComponent: AppComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                val calendarActionCreator = CalendarActionCreator(user)
                loadActionCreatorComponent = DaggerAppComponent.builder()
                        .calendarActionCreatorModule(CalendarActionCreatorModule(calendarActionCreator))
                        .teamActionCreatorModule(TeamActionCreatorModule(TeamActionCreator(user)))
                        .superlativeActionCreatorModule(SuperlativeActionCreatorModule(SuperlativeActionCreator(user)))
                        .standingsActionCreatorModule(StandingsActionCreatorModule(StandingsActionCreator()))
                        .userModule(UserModule(user))
                        .build()

                initializeAfterSignIn(user)
            } else {
                startActivityForResult(getSignInActivity(), SIGN_IN)
            }
        }
    }

    private fun getSignInActivity(): Intent {
        return AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(mutableListOf(
                        AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                        AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                )).build()
    }

    private fun initializeAfterSignIn(user: FirebaseUser) {
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                .apply {
                    setSupportActionBar(toolbar)
                    if (supportActionBar != null) {
                        (supportActionBar as ActionBar).setDisplayHomeAsUpEnabled(true)
                        drawerToggle = ActionBarDrawerToggle(this@MainActivity,
                                drawer, toolbar, R.string.drawer_open, R.string.drawer_close)
                        drawerToggle.isDrawerIndicatorEnabled = true
                        drawerToggle.syncState()
                        navigationView.setNavigationItemSelectedListener { item ->
                            selectDrawerItem(item, drawer)
                            true
                        }
                        navigationView.setCheckedItem(R.id.calendar)
                        swapFragments(CalendarFragment.newInstance())
                    }
                    drawer.addDrawerListener(drawerToggle)
                    val headerLayout = navigationView.inflateHeaderView(R.layout.nav_header)
                    (headerLayout.findViewById(R.id.user_name) as TextView).text = user.displayName
                }
    }

    private fun selectDrawerItem(item: MenuItem, drawer: DrawerLayout) {
        when (item.itemId) {
            R.id.calendar -> swapFragments(CalendarFragment.newInstance())
            R.id.team -> swapFragments(TeamFragment.newInstance())
            R.id.standings -> swapFragments(StandingsFragment.newInstance())
            R.id.logout -> signOutUser()
        }

        item.isChecked = true
        drawer.closeDrawers()
    }

    private fun swapFragments(fragment: LifecycleFragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }

    private fun signOutUser() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener { _ ->
                    startActivity(getSignInActivity())
                }
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

    override fun onPause() {
        super.onPause()
        authStateListener.apply {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }

    override fun onResume() {
        super.onResume()
        firebaseAuth.addAuthStateListener(authStateListener)
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
