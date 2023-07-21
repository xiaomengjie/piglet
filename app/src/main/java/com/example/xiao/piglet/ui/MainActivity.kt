package com.example.xiao.piglet.ui

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.xiao.piglet.R
import com.example.xiao.piglet.databinding.ActivityMainBinding
import com.example.xiao.piglet.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

//    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(viewBinding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.i("MainActivity", "onCreate: ${destination.displayName}")
        }
        // Setup the bottom navigation view with navController
        viewBinding.navBottom.setupWithNavController(navController)
    }
}