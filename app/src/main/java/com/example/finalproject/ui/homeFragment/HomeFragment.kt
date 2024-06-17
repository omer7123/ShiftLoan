package com.example.finalproject.ui.homeFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}