package com.example.finalproject.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavControllerWithBottomNav()
        setupNavigationListener()
        initDestination()
    }

    private fun initDestination() {
        navController.addOnDestinationChangedListener { navController, destination, arguments ->

            if (destination.id == R.id.homeAuthenticationFragment){
//                window.statusBarColor = ContextCompat.getColor(this, R.color.bg_splash)
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
//            if (destination.id == R.id.registerFragment ||
//                destination.id == R.id.authFragment
//
//            ) {
//                binding.navView.visibility = View.GONE
//            } else {
//                binding.navView.visibility = View.VISIBLE
//            }
        }
    }

    private fun setupNavigationListener() {
        binding.bottomNav.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.home_item -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.menu_item -> {
//                    navController.navigate(R.id.fragment_exercises)
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun initNavControllerWithBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
    }
}