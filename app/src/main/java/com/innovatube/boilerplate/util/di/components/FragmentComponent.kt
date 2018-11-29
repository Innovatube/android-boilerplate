package com.innovatube.boilerplate.util.di.components

import com.innovatube.boilerplate.presentation.home.HomeFragment
import com.innovatube.boilerplate.presentation.home.top.FeatureFragment
import com.innovatube.boilerplate.presentation.home.top.TopFragment
import com.innovatube.boilerplate.util.di.modules.FragmentModule
import com.innovatube.boilerplate.util.di.scopes.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [(FragmentModule::class)])
interface FragmentComponent {
    fun inject(homeFragment: TopFragment)
    fun inject(featureFragment: FeatureFragment)
    fun inject(homeFragment: HomeFragment)
}
