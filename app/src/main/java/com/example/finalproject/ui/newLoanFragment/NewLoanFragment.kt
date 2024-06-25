package com.example.finalproject.ui.newLoanFragment

import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
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
import com.example.finalproject.databinding.FragmentNewLoanBinding
import com.example.finalproject.domain.entity.LoanRequestEntity
import com.example.finalproject.domain.entity.LoanRequestWithoutUserData
import com.example.finalproject.presentation.multiViewModelFactory.MultiViewModelFactory
import com.example.finalproject.presentation.newLoanFragment.CreateNewLoanStatusState
import com.example.finalproject.presentation.newLoanFragment.NewLoanViewModel
import com.example.finalproject.ui.homeFragment.HomeFragment
import com.example.finalproject.util.expand
import com.example.finalproject.util.getAppComponent
import com.example.finalproject.util.showToast
import com.example.finalproject.util.shrink
import javax.inject.Inject


class NewLoanFragment : Fragment() {

    private var _binding: FragmentNewLoanBinding? = null
    private val binding get() = requireNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: NewLoanViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[NewLoanViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().loanComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewLoanBinding.inflate(layoutInflater)

        viewModel.createNewLoanStatus.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        return binding.root
    }

    private fun render(state: CreateNewLoanStatusState) {
        when (state) {
            is CreateNewLoanStatusState.Error -> renderError(state.msg)
            CreateNewLoanStatusState.Loading -> renderLoading()
            is CreateNewLoanStatusState.Success -> renderSuccess(state)
        }
    }

    private fun renderSuccess(state: CreateNewLoanStatusState.Success) {
        if (state.loan.state == APPROVED || state.loan.state == REGISTERED) {
            val amount = state.loan.amount.toInt()
            val bundle = Bundle()
            bundle.putInt(AMOUNT, amount)
            findNavController().navigate(
                R.id.action_newLoanFragment_to_successNewLoanFragment,
                bundle
            )
        }

        if (state.loan.state == REJECTED) {
            findNavController().navigate(R.id.action_newLoanFragment_to_errorNewLoanFragment)
        }
    }

    private fun renderLoading() {
        binding.content.shrink()
        binding.content.isClickable = false
        binding.progressCircular.isVisible = true
        binding.progressCircular.scaleX = 0f
        binding.progressCircular.scaleY = 0f
        binding.progressCircular.expand()
    }

    private fun renderError(msg: String?) {
        if (msg.isNullOrEmpty()) {
            requireContext().showToast(getString(R.string.unknown_error))
        } else
            requireContext().showToast(msg)

        binding.content.isClickable = true
        binding.content.expand()
        binding.progressCircular.isVisible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        setupValidation()
    }

    private fun initListener() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.nextBtn.setOnClickListener {
            if (validateFields()) {
                val name = binding.nameTv.text.toString()
                val lastName = binding.lastnameTv.text.toString()
                val phone = binding.phoneTv.text.toString()

                val dataWithoutUser =
                    requireArguments().getSerializable(HomeFragment.DATA) as LoanRequestWithoutUserData
                val amount = dataWithoutUser.amount
                val percent = dataWithoutUser.percent
                val period = dataWithoutUser.period

                viewModel.createLoan(
                    LoanRequestEntity(
                        amount,
                        name,
                        lastName,
                        percent,
                        period,
                        phone
                    )
                )
            }
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        if (binding.lastnameTv.text.toString().isEmpty()) {
            binding.containerLastname.error = getString(R.string.field_cannot_be_empty)
            isValid = false
        } else {
            binding.containerLastname.error = null
        }

        if (binding.nameTv.text.toString().isEmpty()) {
            binding.containerName.error = getString(R.string.field_cannot_be_empty)
            isValid = false
        } else {
            binding.containerName.error = null
        }

        if (binding.phoneTv.text.toString().isEmpty()) {
            binding.containerPhone.error = getString(R.string.field_cannot_be_empty)
            isValid = false
        } else {
            binding.containerPhone.error = null
        }

        return isValid
    }

    private fun setupValidation() {
        binding.nameTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pattern = Regex("^[А-Яа-яЁё ]+\$")
                if (!pattern.matches(s.toString()) && s.toString().isNotEmpty()) {
                    binding.containerName.error =
                        getString(R.string.the_name_must_contain_only_cyrillic)
                } else {
                    binding.containerName.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.lastnameTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pattern = Regex("^[А-Яа-яЁё ]+\$")
                if (!pattern.matches(s.toString()) && s.toString().isNotEmpty()) {
                    binding.containerLastname.error =
                        getString(R.string.lastname_must_contain_only_cyrillic)
                } else {
                    binding.containerLastname.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.phoneTv.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        binding.phoneTv.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val phoneNumber = s.toString()
                val russianPhonePattern = "^\\+7 \\d{3} \\d{3}-\\d{2}-\\d{2}$"

                if (!phoneNumber.matches(Regex(russianPhonePattern))) {
                    binding.containerPhone.error = getString(R.string.invalid_phone_number)
                } else {
                    binding.containerPhone.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                val phoneNumber = s.toString()

                if (!phoneNumber.startsWith("+7")) {
                    binding.phoneTv.removeTextChangedListener(this)
                    binding.phoneTv.setText("+7$phoneNumber")
                    binding.phoneTv.setSelection(binding.phoneTv.text?.length ?: 0)
                    binding.phoneTv.addTextChangedListener(this)
                }
            }
        })
    }

    companion object {
        const val APPROVED = "APPROVED"
        const val REGISTERED = "REGISTERED"
        const val REJECTED = "REJECTED"
        const val AMOUNT = "AMOUNT"
    }
}