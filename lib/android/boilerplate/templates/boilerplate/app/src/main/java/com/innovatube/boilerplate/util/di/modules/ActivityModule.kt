package <%= package_name %>.util.di.modules

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    internal fun provideAppCompatActivity(): AppCompatActivity {
        return this.activity
    }

    @Named("activityContext")
    @Provides
    internal fun provideActivityContext(): Context {
        return this.activity
    }

    @Provides
    internal fun provideSupportFragmentManager(): FragmentManager {
        return activity.supportFragmentManager
    }
}
