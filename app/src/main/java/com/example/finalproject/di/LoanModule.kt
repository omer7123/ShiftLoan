package com.example.finalproject.di

import androidx.lifecycle.ViewModel
import com.example.finalproject.presentation.homeFragment.HomeViewModel
import com.example.finalproject.presentation.loanDetailFragment.LoanDetailViewModel
import com.example.finalproject.presentation.loansFragment.LoansViewModel
import com.example.finalproject.presentation.newLoanFragment.NewLoanViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface LoanModule {
    @Binds
    @[IntoMap ClassKey(NewLoanViewModel::class)]
    fun provideNewLoanViewModel(newLoanViewModel: NewLoanViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(HomeViewModel::class)]
    fun provideHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(LoansViewModel::class)]
    fun provideLoansViewModel(loansViewModel: LoansViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(LoanDetailViewModel::class)]
    fun provideLoanDetailViewModel(loanDetailViewModel: LoanDetailViewModel): ViewModel
}