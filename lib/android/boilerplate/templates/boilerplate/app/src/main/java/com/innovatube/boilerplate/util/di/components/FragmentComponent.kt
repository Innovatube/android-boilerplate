package <%= package_name %>.util.di.components

import <%= package_name %>.presentation.home.HomeFragment
import <%= package_name %>.presentation.home.top.FeatureFragment
import <%= package_name %>.presentation.home.top.TopFragment
import <%= package_name %>.util.di.modules.FragmentModule
import <%= package_name %>.util.di.scopes.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [(FragmentModule::class)])
interface FragmentComponent {
    fun inject(homeFragment: TopFragment)
    fun inject(featureFragment: FeatureFragment)
    fun inject(homeFragment: HomeFragment)
}
