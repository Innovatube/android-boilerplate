package com.innovatube.boilerplate.util.di.activitymodule

import android.arch.lifecycle.ViewModel
import com.innovatube.boilerplate.presentation.home.top.FeatureFragment
import com.innovatube.boilerplate.presentation.home.top.FeatureViewModel
import com.innovatube.boilerplate.presentation.home.top.TopFragment
import com.innovatube.boilerplate.presentation.home.top.TopViewModel
import com.innovatube.boilerplate.util.di.ViewModelKey
import com.innovatube.boilerplate.util.di.scope.ChildFragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface HomeFragmentModule {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    fun contributeTopFragment(): TopFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector
    fun contributeFeatureFragment(): FeatureFragment

    @Binds @IntoMap
    @ViewModelKey(TopViewModel::class)
    fun bindTopViewModel(
        topViewModel: TopViewModel
    ): ViewModel

    @Binds @IntoMap
    @ViewModelKey(FeatureViewModel::class)
    fun bindFeatureViewModel(
        featureViewModel: FeatureViewModel
    ): ViewModel
}