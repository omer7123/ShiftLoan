package com.example.finalproject.ui.homeFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.presentation.homeFragment.HomeScreenState
import com.example.finalproject.presentation.homeFragment.HomeViewModel
import com.example.finalproject.presentation.multiViewModelFactory.MultiViewModelFactory
import com.example.finalproject.util.getAppComponent
import com.example.finalproject.util.showToast
import javax.inject.Inject


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter = LoanAdapter()

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().loanComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            render(state)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.screenState.value == HomeScreenState.Initial)
            viewModel.getLoanConditions()
        initView()
        initListener()
    }

    private fun render(state: HomeScreenState) {
        when (state) {
            is HomeScreenState.Content -> renderContent(state)
            is HomeScreenState.Error -> requireContext().showToast(state.msg)
            HomeScreenState.Initial -> {}
            HomeScreenState.Loading -> renderLoading()
        }
    }

    private fun renderLoading() {
        binding.bannerShimmer.visibility = View.VISIBLE
        binding.loanAmountShimmer.visibility = View.VISIBLE
        binding.newLoanShimmer.visibility = View.VISIBLE
        binding.myLoansTvShimmer.visibility = View.VISIBLE
        binding.myLoansContainerShimmer.visibility = View.VISIBLE

        binding.bannerContainer.visibility = View.INVISIBLE
        binding.loanAmountTv.visibility = View.INVISIBLE
        binding.newLoadContainer.visibility = View.INVISIBLE
        binding.myLoansTv.visibility = View.INVISIBLE
        binding.myLoansContainer.visibility = View.INVISIBLE

        binding.loanEmptyTv.isVisible = false

        binding.bannerShimmer.startShimmer()
        binding.loanAmountShimmer.startShimmer()
        binding.newLoanShimmer.startShimmer()
        binding.myLoansTvShimmer.startShimmer()
        binding.myLoansContainerShimmer.startShimmer()
    }

    private fun renderContent(state: HomeScreenState.Content) {
        binding.bannerShimmer.visibility = View.GONE
        binding.loanAmountShimmer.visibility = View.GONE
        binding.newLoanShimmer.visibility = View.GONE
        binding.myLoansTvShimmer.visibility = View.GONE
        binding.myLoansContainerShimmer.visibility = View.GONE

        binding.bannerContainer.visibility = View.VISIBLE
        binding.loanAmountTv.visibility = View.VISIBLE
        binding.newLoadContainer.visibility = View.VISIBLE
        binding.myLoansTv.visibility = View.VISIBLE
        binding.myLoansContainer.visibility = View.VISIBLE

        binding.bannerShimmer.stopShimmer()
        binding.loanAmountShimmer.stopShimmer()
        binding.newLoanShimmer.stopShimmer()
        binding.myLoansTvShimmer.stopShimmer()
        binding.myLoansContainerShimmer.stopShimmer()

        binding.sumEt.setText(state.sumLoan)
        val conditions =
            getString(
                R.string.conditions_loan,
                state.conditions.percent.toString(),
                state.conditions.period.toString()
            )
        binding.conditionsTv.text = conditions
        binding.sumSb.max = state.conditions.maxAmount
        binding.maxTv.text =
            getString(R.string.max_value_predel, state.conditions.maxAmount.toString())

        if (state.sumLoan.toInt() < 1000) {
            binding.validationTv.text = getString(R.string.minimum_1000)
        } else if (state.sumLoan.toInt() > state.conditions.maxAmount) {
            binding.validationTv.text =
                getString(R.string.max_value_loan, state.conditions.maxAmount.toString())
        } else {
            binding.validationTv.text = ""
        }

        if (state.list.isNotEmpty()) {
            binding.loanEmptyTv.isVisible = false
            binding.myLoansContainer.isVisible = true
            adapter.submitList(state.list)
        } else {
            binding.loanEmptyTv.isVisible = true
            binding.myLoansContainer.isVisible = false
        }
    }

    private fun initView() {
        binding.loanRv.adapter = adapter

        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.editIv.setOnClickListener {
            binding.sumEt.isFocusable = true
            binding.sumEt.isFocusableInTouchMode = true
            binding.sumEt.requestFocus()
            binding.sumEt.selectAll()

            imm.showSoftInput(binding.sumEt, InputMethodManager.SHOW_IMPLICIT)
        }

        binding.sumEt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.sumEt.clearFocus()
                binding.sumEt.isFocusableInTouchMode = false

                imm.hideSoftInputFromWindow(binding.sumEt.windowToken, 0)
                val value = binding.sumEt.text.toString()

                if (value.toInt() <= binding.sumSb.max)
                    binding.sumSb.progress = value.toInt()
                else {
                    binding.sumSb.progress = binding.sumSb.max
                }
                viewModel.setValueLoan(binding.sumEt.text.toString())
                true
            } else {
                false
            }
        }

        binding.sumEt.setOnFocusChangeListener { _, hasFocus ->
            binding.sumEt.isCursorVisible = hasFocus
            if (!hasFocus) {
                binding.sumEt.clearFocus()
            }
        }
    }

    private fun initListener() {
        binding.questionIv.setOnClickListener {
            findNavController().navigate(R.id.boardFragment)
        }

        binding.sumSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (binding.sumEt.text.toString().toInt() <= seekBar!!.max) {
                    binding.sumEt.setText(progress.toString())
                    viewModel.setValueLoan(progress.toString())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (binding.sumEt.text.toString().toInt() > seekBar!!.max)
                    viewModel.setValueLoan(seekBar.progress.toString())
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        binding.checkAllBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loansFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}