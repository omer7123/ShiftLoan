package com.example.finalproject.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.presentation.mainActivity.MainViewModel
import com.example.finalproject.presentation.multiViewModelFactory.MultiViewModelFactory
import com.example.finalproject.util.getAppComponent
import java.util.Locale
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavbarHider {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var navController: NavController

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent().inject(this)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavControllerWithBottomNav()
        setupNavigationListener()
        viewModel.locale.observe(this) { locale ->
            renderLocale(locale)
        }
        viewModel.getLocale()
    }

    private fun renderLocale(language: String) {

        val locale: Locale = if (language.isNotEmpty())
            Locale(language)
        else
            Locale("ru")

        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)

        val context = createConfigurationContext(config)
        resources.updateConfiguration(config, context.resources.displayMetrics)
        updateBottomNavTitles()
    }

    private fun updateBottomNavTitles() {
        binding.bottomNav.menu.findItem(R.id.homeFragment).title = getString(R.string.home)
        binding.bottomNav.menu.findItem(R.id.menuFragment).title = getString(R.string.menu)
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