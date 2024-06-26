package com.example.finalproject.ui.homeAuthenticationFragment

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentHomeAuthenticationBinding
import com.example.finalproject.presentation.homeAuthenticationFragment.HomeAuthenticationAuthStatus
import com.example.finalproject.presentation.homeAuthenticationFragment.HomeAuthenticationViewModel
import com.example.finalproject.presentation.multiViewModelFactory.MultiViewModelFactory
import com.example.finalproject.ui.NavbarHider
import com.example.finalproject.ui.authFragment.AuthenticationFragment
import com.example.finalproject.util.NetworkReceiver
import com.example.finalproject.util.getAppComponent
import javax.inject.Inject


class HomeAuthenticationFragment : Fragment() {

    private var _binding: FragmentHomeAuthenticationBinding? = null
    private val binding get() = requireNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: HomeAuthenticationViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeAuthenticationViewModel::class.java]
    }

    private val networkReceiver = NetworkReceiver()
    private var navbarHider: NavbarHider? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().authenticationComponent().create().inject(this)
    }

    override fun onStart() {
        super.onStart()

        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(networkReceiver, intentFilter)

        networkReceiver.isConnected.observe(viewLifecycleOwner) {
            if (it) {
                val currentState = viewModel.authStatus.value
                if (currentState is HomeAuthenticationAuthStatus.Error) {
                    viewModel.auth()
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAuthenticationBinding.inflate(layoutInflater)
        viewModel.authStatus.observe(viewLifecycleOwner) { status ->
            render(status)
        }

        if (context is NavbarHider) {
            navbarHider = context as NavbarHider
            navbarHider!!.setNavbarVisibility(false)
        }

        viewModel.auth()
        return binding.root
    }

    private fun render(status: HomeAuthenticationAuthStatus) {
        when (status) {
            is HomeAuthenticationAuthStatus.Error -> renderError()
            HomeAuthenticationAuthStatus.Loading -> renderLoading()
            HomeAuthenticationAuthStatus.Success -> renderSuccess()
        }
    }

    private fun renderSuccess() {
        binding.progressCircular.isVisible = false
        findNavController().navigate(R.id.action_homeAuthenticationFragment_to_homeFragment)
    }

    private fun renderLoading() {
        binding.progressCircular.isVisible = true
    }

    private fun renderError() {
        binding.progressCircular.isVisible = false
        val bottomSheetFragment = AuthenticationFragment()
        bottomSheetFragment.isCancelable = false
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}