package <%= package_name %>.util.di.activitymodule

import android.arch.lifecycle.ViewModel
import <%= package_name %>.presentation.home.top.FeatureFragment
import <%= package_name %>.presentation.home.top.FeatureViewModel
import <%= package_name %>.presentation.home.top.TopFragment
import <%= package_name %>.presentation.home.top.TopViewModel
import <%= package_name %>.util.di.ViewModelKey
import <%= package_name %>.util.di.scope.ChildFragmentScoped
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
