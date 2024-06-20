package com.example.finalproject.di

import com.example.finalproject.ui.homeFragment.HomeFragment
import com.example.finalproject.ui.loansFragment.LoansFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface LoanComponent {
    fun inject(fragment: HomeFragment)
    fun inject(fragment: LoansFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoanComponent
    }
}