package com.example.finalproject.ui.specialOfferFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentSpecialOfferBinding


class SpecialOfferFragment : Fragment() {

    private var _binding: FragmentSpecialOfferBinding? = null
    private val binding get() = requireNotNull(_binding)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpecialOfferBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_specialOfferFragment_to_addressOfBankFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}