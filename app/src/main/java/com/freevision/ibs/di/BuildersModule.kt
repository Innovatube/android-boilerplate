package com.freevision.ibs.di

import com.freevision.ibs.view.example.ExampleActivity
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
