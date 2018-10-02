package <%= package_name %>.presentation.base

import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import <%= package_name %>.InnovatubeApplication
import <%= package_name %>.util.di.components.ActivityComponent
import <%= package_name %>.util.di.modules.ActivityModule


abstract class BaseActivity : AppCompatActivity() {

    val component: ActivityComponent by lazy {
        (application as InnovatubeApplication).getComponent().plus(ActivityModule(this))
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
