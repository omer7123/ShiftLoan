package com.example.finalproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavbarHider {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavControllerWithBottomNav()
        setupNavigationListener()

    }

    private fun setupNavigationListener() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.homeFragment) {
                val currentDestination = navController.currentDestination
                if (currentDestination?.id == R.id.homeFragment) {

                    return@setOnItemSelectedListener false
                }
            }
            NavigationUI.onNavDestinationSelected(item, navController)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> setActualItem(R.id.homeFragment)
                R.id.menuFragment -> setActualItem(R.id.menuFragment)
            }
        }
    }

    private fun initNavControllerWithBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
    }

    private fun setActualItem(id: Int) {
        if (binding.bottomNav.selectedItemId != id)
            binding.bottomNav.selectedItemId = id
    }

    override fun setNavbarVisibility(it: Boolean) {
        binding.bottomNav.isVisible = it
    }
}

interface NavbarHider {
    fun setNavbarVisibility(it: Boolean)

}