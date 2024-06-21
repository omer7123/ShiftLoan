package com.example.finalproject.ui.boardFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentBoardBinding
import com.example.finalproject.ui.NavbarHider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class BoardFragment : Fragment() {

    private var _binding: FragmentBoardBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var navbarHider: NavbarHider? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoardBinding.inflate(layoutInflater)

        if (context is NavbarHider) {
            navbarHider = context as NavbarHider
            navbarHider!!.setNavbarVisibility(false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()

    }

    private fun initViewPager() {
        val adapter = BoardAdapter(titles(), images(), firstDescribe(), secondDescribe())
        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.prevTv.isVisible = position != 0
                if (position == adapter.itemCount - 1) {
                    binding.nextTv.text = getString(R.string.close)
                } else {
                    binding.nextTv.text = getString(R.string.next)
                }

                if (position == 0) {
                    binding.prevTv.isVisible = false
                } else {
                    binding.prevTv.isVisible = true
                }
            }
        })

        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab: TabLayout.Tab?, _: Int ->
            tab?.setCustomView(R.layout.cutsom_tab)
        }.attach()

        binding.nextTv.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                binding.viewPager.setCurrentItem(currentItem + 1, true)
            } else {
                findNavController().navigate(R.id.action_boardFragment_to_homeFragment)
            }
        }

        binding.prevTv.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem > 0) {
                binding.viewPager.setCurrentItem(currentItem - 1, true)
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_boardFragment_to_homeFragment)
        }
    }

    private fun titles(): Array<String> {
        return arrayOf(
            getString(R.string.board_title_1),
            getString(R.string.board_title_2),
            getString(R.string.board_title_3)
        )
    }

    private fun images(): Array<Int> {
        return arrayOf(
            R.drawable.ic_illuctration,
            R.drawable.ic_illustration_1,
            R.drawable.ic_illustration_2
        )
    }

    private fun firstDescribe(): Array<String> {
        return arrayOf(
            getString(R.string.board_first_describe_1),
            getString(R.string.board_first_describe_2),
            getString(R.string.board_first_describe_3),
        )
    }

    private fun secondDescribe(): Array<String> {
        return arrayOf(
            getString(R.string.board_second_describe_1),
            getString(R.string.board_second_describe_2),
            getString(R.string.empty)
        )
    }
}