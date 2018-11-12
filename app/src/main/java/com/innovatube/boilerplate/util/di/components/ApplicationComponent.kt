package com.innovatube.boilerplate.util.di.components

import android.app.Application
import com.innovatube.boilerplate.util.di.modules.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    DomainModule::class,
    ApiModule::class,
    DatabaseModule::class,
    HeaderDataRepositoryModule::class
])
interface ApplicationComponent {
    fun plus(activityModule: ActivityModule): ActivityComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
