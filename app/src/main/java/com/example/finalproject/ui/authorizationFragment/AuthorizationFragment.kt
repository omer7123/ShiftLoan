package com.example.finalproject.ui.authorizationFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentAuthorizationBinding
import com.example.finalproject.domain.entity.AuthEntity
import com.example.finalproject.presentation.authorizationFragment.AuthorizationStatusState
import com.example.finalproject.presentation.authorizationFragment.AuthorizationViewModel
import com.example.finalproject.presentation.multiViewModelFactory.MultiViewModelFactory
import com.example.finalproject.util.getAppComponent
import com.example.finalproject.util.showToast
import javax.inject.Inject


class AuthorizationFragment : Fragment() {
    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = requireNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: AuthorizationViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthorizationViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().authenticationComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(layoutInflater)
        viewModel.authorizationState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        binding.signBtn.setOnClickListener {
            val name = binding.loginTv.text.toString()
            val password = binding.passwordTv.text.toString()
            viewModel.auth(AuthEntity(name, password))
        }
    }

    private fun render(state: AuthorizationStatusState) {
        when(state){
            is AuthorizationStatusState.Error -> {
                requireContext().showToast(state.msg)
                Log.e("Error", state.msg)
                binding.content.visibility = View.VISIBLE
                binding.progressCircular.isVisible = false
            }
            AuthorizationStatusState.Initial -> {}
            AuthorizationStatusState.Loading -> {
                binding.content.visibility = View.INVISIBLE
                binding.progressCircular.isVisible = true
            }
            AuthorizationStatusState.Success -> findNavController().navigate(R.id.boardFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}