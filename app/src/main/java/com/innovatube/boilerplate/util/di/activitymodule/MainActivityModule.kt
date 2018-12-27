package com.innovatube.boilerplate.util.di.activitymodule

import android.arch.lifecycle.ViewModel
import android.support.v7.app.AppCompatActivity
import com.innovatube.boilerplate.presentation.home.HomeFragment
import com.innovatube.boilerplate.presentation.home.HomeViewModel
import com.innovatube.boilerplate.presentation.main.MainActivity
import com.innovatube.boilerplate.presentation.main.MainViewModel
import com.innovatube.boilerplate.util.di.ViewModelKey
import com.innovatube.boilerplate.util.di.scope.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface MainActivityModule {
    @Binds
    fun providesAppCompatActivity(mainActivity: MainActivity): AppCompatActivity

    @FragmentScoped
    @ContributesAndroidInjector(modules = [
        HomeFragmentModule::class
    ])
    fun contributeHomeFragment(): HomeFragment

    @Binds @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(
        homeViewModel: HomeViewModel
    ): ViewModel

    @Binds @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(
        mainViewModel: MainViewModel
    ): ViewModel
}
