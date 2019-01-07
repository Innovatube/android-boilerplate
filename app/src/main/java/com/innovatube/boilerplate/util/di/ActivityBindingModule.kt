package com.innovatube.boilerplate.util.di

import com.innovatube.boilerplate.presentation.main.MainActivity
import com.innovatube.boilerplate.presentation.main.MainActivityModule
import com.innovatube.boilerplate.util.di.scope.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [
        MainActivityModule::class
    ])
    fun contributeMainActivity(): MainActivity
}
