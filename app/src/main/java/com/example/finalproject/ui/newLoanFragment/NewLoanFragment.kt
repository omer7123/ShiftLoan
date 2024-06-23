package com.example.finalproject.ui.newLoanFragment

import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.finalproject.util.getAppComponent
import com.example.finalproject.util.showToast
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
            is CreateNewLoanStatusState.Error -> requireContext().showToast(state.msg)
            CreateNewLoanStatusState.Loading -> {}
            is CreateNewLoanStatusState.Success -> {
                if (state.loan.state == APPROVED || state.loan.state == REGISTERED) {
                    val amount = state.loan.amount.toInt()
                    val bundle = Bundle()
                    bundle.putInt(AMOUNT, amount)
                    findNavController().navigate(
                        R.id.action_newLoanFragment_to_successNewLoanFragment,
                        bundle
                    )
                }
            }
        }
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
            var error1 = binding.containerLastname.error
            var error2 = binding.containerName.error
            var error3 = binding.containerPhone.error
            if (error1.isNullOrEmpty() && error2.isNullOrEmpty() && error3.isNullOrEmpty()) {
                if (binding.phoneTv.text.toString().isEmpty())
                    binding.containerPhone.error = "Поле не может быть пустым"
                if (binding.nameTv.text.toString().isEmpty())
                    binding.containerName.error = "Поле не может быть пустым"

                if (binding.lastnameTv.text.toString().isEmpty())
                    binding.containerLastname.error = "Поле не может быть пустым"

                error1 = binding.containerLastname.error
                error2 = binding.containerName.error
                error3 = binding.containerPhone.error
                if (error1.isNullOrEmpty() && error2.isNullOrEmpty() && error3.isNullOrEmpty()) {

                    val name = binding.nameTv.text.toString()
                    val lastName = binding.lastnameTv.text.toString()
                    val phone = binding.phoneTv.text.toString()

                    val dataWithoutUser =
                        arguments!!.getSerializable(HomeFragment.DATA) as LoanRequestWithoutUserData
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
    }

    private fun setupValidation() {
        binding.nameTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pattern = Regex("^[А-Яа-яЁё ]+\$")
                if (!pattern.matches(s.toString()) && s.toString().isNotEmpty()) {
                    binding.containerName.error = "Имя должно содержать только кириллицу"
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
                    binding.containerLastname.error = "Фамилия должна содержать только кириллицу"
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
                    binding.containerPhone.error = "Некорректный номер телефона"
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