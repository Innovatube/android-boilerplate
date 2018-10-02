package <%= package_name %>.util.di.components

import <%= package_name %>.presentation.main.MainActivity
import <%= package_name %>.util.di.modules.ActivityModule
import <%= package_name %>.util.di.modules.FragmentModule
import <%= package_name %>.util.di.scopes.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {
    fun plus(module: FragmentModule): FragmentComponent
    fun inject(mainActivity: MainActivity)

}
