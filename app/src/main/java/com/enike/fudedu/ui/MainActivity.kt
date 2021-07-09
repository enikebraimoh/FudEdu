package com.enike.fudedu.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.enike.fudedu.R
import com.enike.fudedu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // to find the nav controller from the nav host fragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        // for bottom navigation
        setupNavigation()
        binding.bottomNavigation.setupWithNavController(navController)

        //for appbar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(this, navController)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

    }

    private fun setupNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                    supportActionBar?.hide()
                }
                R.id.calenderFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                    supportActionBar?.hide()
                }
                R.id.settingsFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                    supportActionBar?.hide()
                }
                R.id.messagesFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                    supportActionBar?.hide()
                }
                else -> {
                    binding.bottomNavigation.visibility = View.GONE
                    supportActionBar?.show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}