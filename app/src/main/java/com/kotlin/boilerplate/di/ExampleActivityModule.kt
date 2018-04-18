package com.kotlin.boilerplate.di

import com.kotlin.boilerplate.data.DataManager
import com.kotlin.boilerplate.presentation.ExampleViewModelFactory
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