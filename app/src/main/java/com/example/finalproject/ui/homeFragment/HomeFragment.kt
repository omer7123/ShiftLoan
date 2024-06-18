package com.example.finalproject.ui.homeFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.presentation.homeFragment.HomeScreenState
import com.example.finalproject.presentation.homeFragment.HomeViewModel
import com.example.finalproject.presentation.multiViewModelFactory.MultiViewModelFactory
import com.example.finalproject.util.getAppComponent
import javax.inject.Inject


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

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
        viewModel.getLoanConditions()
        initView()
        initListener()
    }

    private fun render(state: HomeScreenState) {
        when (state) {
            is HomeScreenState.Content -> renderContent(state)
            is HomeScreenState.Error -> {}
            HomeScreenState.Initial -> {}
        }
    }

    private fun renderContent(state: HomeScreenState.Content) {
        Log.e("State", state.sumLoan)
        val conditions = "Под ${state.conditions.percent}% на ${state.conditions.period} дней"
        binding.conditionsTv.text = conditions
        binding.sumSb.max = state.conditions.maxAmount
        binding.maxTv.text = "${state.conditions.maxAmount} ₽"

        if (state.sumLoan.toInt() < 1000) {
            binding.validationTv.text = "Минимум 1 000 ₽"
        } else if (state.sumLoan.toInt() > state.conditions.maxAmount) {
            binding.validationTv.text = "Максимум ${state.conditions.maxAmount} ₽"
        } else {
            binding.validationTv.text = ""
        }
        binding.sumEt.setText(state.sumLoan)
    }

    private fun initView() {
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}