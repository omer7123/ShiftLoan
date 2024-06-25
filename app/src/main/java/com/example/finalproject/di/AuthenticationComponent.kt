package com.example.finalproject.di


import com.example.finalproject.ui.authorizationFragment.AuthorizationFragment
import com.example.finalproject.ui.homeAuthenticationFragment.HomeAuthenticationFragment
import com.example.finalproject.ui.menuFragment.MenuFragment
import com.example.finalproject.ui.registrationFragment.RegistrationFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [AuthenticationModule::class])
interface AuthenticationComponent {
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: AuthorizationFragment)
    fun inject(fragment: HomeAuthenticationFragment)
    fun inject(fragment: MenuFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthenticationComponent
    }
}