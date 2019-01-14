package com.innovatube.boilerplate.util.di

import android.app.Application
import com.innovatube.boilerplate.InnovatubeApplication
import com.innovatube.boilerplate.util.di.module.ActivityBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    AppModule::class,
    DatabaseModule::class,
    DataSourceRepositoryModule::class,
    DomainModule::class,
    NetworkModule::class
])
interface AppComponent : AndroidInjector<InnovatubeApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun networkModule(networkModule: NetworkModule): Builder
        fun databaseModule(databaseModule: DatabaseModule): Builder
        fun build(): AppComponent
    }

    override fun inject(instance: InnovatubeApplication)
}
