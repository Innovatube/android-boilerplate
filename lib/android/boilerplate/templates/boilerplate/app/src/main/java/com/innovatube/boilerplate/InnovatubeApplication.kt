package <%= package_name %>

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ProcessLifecycleOwner
import <%= package_name %>.util.di.components.ApplicationComponent
import <%= package_name %>.util.di.components.DaggerApplicationComponent
import com.orhanobut.hawk.Hawk
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class InnovatubeApplication : Application(), LifecycleObserver {

    private lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        RxJavaPlugins.setErrorHandler { e -> Timber.e(e.toString()) }

        appComponent = DaggerApplicationComponent.builder()
            .application(this)
            .build()
        Hawk.init(this).build()

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    fun getComponent() = appComponent

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
    }
}
