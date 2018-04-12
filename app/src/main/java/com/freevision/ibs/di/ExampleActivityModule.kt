package com.freevision.ibs.di

import com.freevision.ibs.data.DataManager
import com.freevision.ibs.presentation.ExampleViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by anhtuan on 3/6/18.
 */
@Module
class ExampleActivityModule {
    @Provides
    fun provideExampleViewModel(dataManager: DataManager): ExampleViewModelFactory {
        return ExampleViewModelFactory(dataManager)
    }
}