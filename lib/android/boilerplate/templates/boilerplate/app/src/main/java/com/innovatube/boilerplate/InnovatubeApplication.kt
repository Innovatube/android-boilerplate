package <%= package_name %>

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ProcessLifecycleOwner
import <%= package_name %>.util.di.DaggerAppComponent
import <%= package_name %>.util.di.DatabaseModule
import <%= package_name %>.util.di.NetworkModule
import com.orhanobut.hawk.Hawk
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class InnovatubeApplication : DaggerApplication(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        RxJavaPlugins.setErrorHandler { e -> Timber.e(e.toString()) }

        Hawk.init(this).build()

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .networkModule(NetworkModule())
            .databaseModule(DatabaseModule())
            .build()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
    }
}
