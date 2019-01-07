package <%= package_name %>.util.di

import <%= package_name %>.presentation.main.MainActivity
import <%= package_name %>.util.di.activitymodule.MainActivityModule
import <%= package_name %>.util.di.scope.ActivityScoped
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
