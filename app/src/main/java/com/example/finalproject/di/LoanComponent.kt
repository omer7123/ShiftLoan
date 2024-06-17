package com.example.finalproject.di

import com.example.finalproject.ui.homeFragment.HomeFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface LoanComponent {
    fun inject(fragment: HomeFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoanComponent
    }
}