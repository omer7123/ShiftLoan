package com.example.finalproject.di


import com.example.finalproject.ui.changeLanguageFragment.ChangeLanguageFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface SettingsComponent {
    fun inject(fragment: ChangeLanguageFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): SettingsComponent
    }
}