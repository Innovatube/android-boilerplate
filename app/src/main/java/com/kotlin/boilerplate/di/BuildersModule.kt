package com.kotlin.boilerplate.di

import com.kotlin.boilerplate.view.example.ExampleActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Binds all sub-components within the app.
 */
@Module
abstract class BuildersModule {
    @ContributesAndroidInjector(modules = [(ExampleActivityModule::class)])
    abstract fun bindExampleActivity(): ExampleActivity
}
