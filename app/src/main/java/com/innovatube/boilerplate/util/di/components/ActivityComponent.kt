package com.innovatube.boilerplate.util.di.components

import com.innovatube.boilerplate.presentation.main.MainActivity
import com.innovatube.boilerplate.util.di.modules.ActivityModule
import com.innovatube.boilerplate.util.di.modules.FragmentModule
import com.innovatube.boilerplate.util.di.scopes.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {
    fun plus(module: FragmentModule): FragmentComponent
    fun inject(mainActivity: MainActivity)
}
