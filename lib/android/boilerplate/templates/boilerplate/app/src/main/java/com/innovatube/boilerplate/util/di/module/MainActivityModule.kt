package <%= package_name %>.util.di.module

import android.arch.lifecycle.ViewModel
import android.support.v7.app.AppCompatActivity
import <%= package_name %>.presentation.home.HomeFragment
import <%= package_name %>.presentation.home.HomeViewModel
import <%= package_name %>.presentation.main.MainActivity
import <%= package_name %>.presentation.main.MainViewModel
import <%= package_name %>.util.di.ViewModelKey
import <%= package_name %>.util.di.scope.FragmentScoped
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
