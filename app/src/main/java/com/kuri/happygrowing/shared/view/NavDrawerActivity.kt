package com.kuri.happygrowing.shared.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.kuri.happygrowing.R
import com.kuri.happygrowing.settings.view.SettingsFragment
import com.kuri.happygrowing.stats.view.CurrentStatsFragment

class NavDrawerActivity: AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mToggle: ActionBarDrawerToggle
    private lateinit var mContainer: FrameLayout
    private lateinit var mStatsFragment: Fragment
    private var mSettingsFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_drawer)

        mContainer = findViewById(R.id.main_container)
        mDrawerLayout = findViewById(R.id.activity_nav_drawer)
        mStatsFragment = CurrentStatsFragment()
        showFragment(mStatsFragment)
        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)

        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        findViewById<NavigationView>(R.id.nv).setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.menu_option_settings -> showFragment(mSettingsFragment ?: SettingsFragment())
                R.id.menu_option_stats -> showFragment(mStatsFragment)
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        if(mToggle.onOptionsItemSelected(item)) true else super.onOptionsItemSelected(item)

    override fun onBackPressed() {
        if(!closeNavigationDrawer()){
            super.onBackPressed()
        }
    }

    private fun showFragment(fragment: Fragment){
        if(mContainer != null) {
            supportFragmentManager.beginTransaction().replace(mContainer.id, fragment).commit()
            closeNavigationDrawer()
        }
    }

    private fun closeNavigationDrawer() =
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
            true
        } else {
            false
        }

}