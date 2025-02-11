package com.example.finalproject.ui.authorizationFragment

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
import com.example.finalproject.databinding.FragmentAuthorizationBinding
import com.example.finalproject.domain.entity.AuthEntity
import com.example.finalproject.presentation.authorizationFragment.AuthorizationStatusState
import com.example.finalproject.presentation.authorizationFragment.AuthorizationViewModel
import com.example.finalproject.presentation.multiViewModelFactory.MultiViewModelFactory
import com.example.finalproject.util.expand
import com.example.finalproject.util.getAppComponent
import com.example.finalproject.util.showToast
import com.example.finalproject.util.shrink
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {
        setupTextChangedListener(binding.loginTv, binding.containerLogin)
        setupTextChangedListener(binding.passwordTv, binding.containerPassword)
    }

    private fun initListener() {
        binding.signBtn.setOnClickListener {
            val name = binding.loginTv.text.toString()
            val password = binding.passwordTv.text.toString()

            if (name.isEmpty()) binding.containerLogin.error =
                getString(R.string.field_cannot_be_empty)
            if (password.isEmpty()) binding.containerPassword.error =
                getString(R.string.field_cannot_be_empty)

            if (name.isNotEmpty() && password.isNotEmpty())
                viewModel.auth(AuthEntity(name, password))
        }
    }

    private fun render(state: AuthorizationStatusState) {
        when (state) {
            is AuthorizationStatusState.Error -> renderError(state.msg, state.code)
            AuthorizationStatusState.Loading -> renderLoading()
            AuthorizationStatusState.Success -> renderSuccess()
        }
    }

    private fun renderSuccess() {
        findNavController().navigate(R.id.action_homeAuthenticationFragment_to_homeFragment)
    }


    private fun renderLoading() {
        binding.content.shrink()
        binding.content.isClickable = false
        binding.progressCircular.isVisible = true
        binding.progressCircular.scaleX = 0f
        binding.progressCircular.scaleY = 0f
        binding.progressCircular.expand()
    }

    private fun renderError(msg: String?, code: Int?) {
        if (msg.isNullOrEmpty()) requireContext().showToast(R.string.unknown_error.toString())
        else {
            if (code == 404) requireContext().showToast(getString(R.string.user_hasnot))
            else requireContext().showToast(msg)
        }
        binding.content.isClickable = true
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