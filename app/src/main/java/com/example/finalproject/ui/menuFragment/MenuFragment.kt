package com.example.finalproject.ui.menuFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initListener() {

        binding.questionIv.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_boardFragment)
        }
        binding.myLoansLayout.content.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_loansFragment)
        }
        binding.offersLayout.content.setOnClickListener {
            findNavController().navigate(R.id.specialOfferFragment)
        }
        binding.bankBranchesLayout.content.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_addressOfBankFragment)
        }
    }

    private fun initView() {
        binding.myLoansLayout.title.text = getString(R.string.my_loans)
        binding.offersLayout.title.text = getString(R.string.offers_for_you)
        binding.bankBranchesLayout.title.text = getString(R.string.bank_branches)
        binding.helpLayout.title.text = getString(R.string.help)
        binding.languageLayout.title.text = getString(R.string.language)
        binding.logOutLayout.title.text = getString(R.string.go_out)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}