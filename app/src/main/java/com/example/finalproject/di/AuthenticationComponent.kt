package com.example.finalproject.di


import com.example.finalproject.ui.authorizationFragment.AuthorizationFragment
import com.example.finalproject.ui.homeAuthenticationFragment.HomeAuthenticationFragment
import com.example.finalproject.ui.registrationFragment.RegistrationFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface AuthenticationComponent {
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: AuthorizationFragment)
    fun inject(fragment: HomeAuthenticationFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthenticationComponent
    }
}