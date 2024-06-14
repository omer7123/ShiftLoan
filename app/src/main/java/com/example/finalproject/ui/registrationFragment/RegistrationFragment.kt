package com.example.finalproject.ui.registrationFragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentRegistrationBinding
import com.example.finalproject.presentation.multiViewModelFactory.MultiViewModelFactory
import com.example.finalproject.presentation.registrationFragment.RegistrationScreenState
import com.example.finalproject.presentation.registrationFragment.RegistrationViewModel
import com.example.finalproject.util.expand
import com.example.finalproject.util.getAppComponent
import com.example.finalproject.util.showToast
import com.example.finalproject.util.shrink
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import javax.inject.Inject


class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = requireNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[RegistrationViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().authenticationComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(layoutInflater)

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        binding.signBtn.setOnClickListener {
            val name = binding.loginTv.text.toString()
            val password = binding.passwordTv.text.toString()
            val confirmPassword = binding.replayPasswordTv.text.toString()
            viewModel.register(name, password, confirmPassword)
        }
    }

    private fun initView() {
        setupTextChangedListener(binding.loginTv, binding.containerLogin)
        setupTextChangedListener(binding.passwordTv, binding.containerPassword)
        setupTextChangedListener(binding.replayPasswordTv, binding.containerReplayPassword)
    }

    private fun render(state: RegistrationScreenState) {
        when (state) {
            is RegistrationScreenState.Error -> renderError(state.msg)
            RegistrationScreenState.Initial -> {}
            RegistrationScreenState.Loading -> renderLoading()
            RegistrationScreenState.Success -> renderSuccess()
            is RegistrationScreenState.ValidationError -> renderValidation(state)
        }
    }

    private fun renderValidation(state: RegistrationScreenState.ValidationError) {
        binding.containerLogin.error = state.nameError
        binding.containerPassword.error = state.passwordError
        binding.containerReplayPassword.error = state.confirmPasswordError
    }

    private fun renderSuccess() {
        findNavController().navigate(R.id.action_homeAuthenticationFragment_to_boardFragment)
    }

    private fun renderLoading() {
        binding.content.shrink()
        binding.progressCircular.isVisible = true
        binding.progressCircular.scaleX = 0f
        binding.progressCircular.scaleY = 0f
        binding.progressCircular.expand()
    }

    private fun renderError(msg: String) {
        requireContext().showToast(msg)
        binding.content.expand()
        binding.progressCircular.isVisible = false
    }

    private fun setupTextChangedListener(
        editText: TextInputEditText,
        textInputLayout: TextInputLayout
    ) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textInputLayout.error = null
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}