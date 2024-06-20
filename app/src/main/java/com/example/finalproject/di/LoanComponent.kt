package com.example.finalproject.di

import com.example.finalproject.ui.homeFragment.HomeFragment
import com.example.finalproject.ui.loanDetailFragment.LoanDetailFragment
import com.example.finalproject.ui.loansFragment.LoansFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface LoanComponent {
    fun inject(fragment: HomeFragment)
    fun inject(fragment: LoansFragment)
    fun inject(fragment: LoanDetailFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoanComponent
    }
}