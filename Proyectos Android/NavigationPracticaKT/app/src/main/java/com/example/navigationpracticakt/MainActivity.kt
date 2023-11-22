package com.example.navigationpracticakt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.navigationpracticakt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        //For the AppBar's Menu to appear we have to give it the layouts from which it will be visible
        //on the rest it will give the option to return to the main layout
        val appBarConfig = AppBarConfiguration.Builder(R.id.tabFragment, R.id.drawer1Fragment, R.id.drawer2Fragment)
            .setOpenableLayout(binding.drawerLayout).build()
        navController = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        //Establishing the navigation to Bottom Navigation View
        NavigationUI.setupWithNavController(binding.bottomNavView, navController)
        //Establishing the navigation to Navigation View
        NavigationUI.setupWithNavController(binding.navView, navController)
        //Establishing the navigation to Toolbar
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfig)
    }

    //Inflating option's menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Configuring the element selection in Options Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, navController) ||
                super.onOptionsItemSelected(item)
    }
}