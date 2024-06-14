package com.example.finalproject.ui.splashScreenFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject.R
import com.example.finalproject.ui.authFragment.AuthenticationFragment


class HomeAuthenticationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheetFragment = AuthenticationFragment()
        bottomSheetFragment.isCancelable = false
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }
}