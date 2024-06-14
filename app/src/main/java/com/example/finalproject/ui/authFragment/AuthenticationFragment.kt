package com.example.finalproject.ui.authFragment


import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentAuthenticationBinding
import com.example.finalproject.ui.authorizationFragment.AuthorizationFragment
import com.example.finalproject.ui.registrationFragment.RegistrationFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout


class AuthenticationFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAuthenticationBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthenticationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        initListener()

        childFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, AuthorizationFragment())
            .commit()
    }

    private fun initView() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.sign_in)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.registration)))
        val root: View = binding.tabLayout.getChildAt(0)
        if (root is LinearLayout) {
            root.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            val drawable = GradientDrawable()
            drawable.setColor(ContextCompat.getColor(requireContext(), R.color.bg_disable))
            drawable.setSize(2, 1)
            root.dividerPadding = 10
            root.dividerDrawable = drawable
        }
    }

    private fun initListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragment = when (tab?.position) {
                    0 -> AuthorizationFragment()
                    1 -> RegistrationFragment()

                    else -> {AuthorizationFragment()}
                }
                fragment.let {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, it)
                        .commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}