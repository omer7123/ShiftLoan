package com.example.finalproject.ui.successNewLoanFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentSuccessNewLoanBinding
import com.example.finalproject.ui.newLoanFragment.NewLoanFragment


class SuccessNewLoanFragment : Fragment() {
    private var _binding: FragmentSuccessNewLoanBinding? = null
    private val binding get() = requireNotNull(_binding)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuccessNewLoanBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initListener() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_successNewLoanFragment_to_addressOfBankFragment)
        }
    }

    private fun initView() {
        val amount = requireArguments().getInt(NewLoanFragment.AMOUNT)
        binding.titleTv.text = getString(R.string.get_money_in_bank, amount.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}