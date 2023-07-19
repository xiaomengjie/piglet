package com.example.www.saveps.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.www.saveps.R
import com.example.www.saveps.base.BaseActivity
import com.example.www.saveps.databinding.ActivityMainBinding

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
        // Setup the bottom navigation view with navController
        viewBinding.navBottom.setupWithNavController(navController)
    }
}