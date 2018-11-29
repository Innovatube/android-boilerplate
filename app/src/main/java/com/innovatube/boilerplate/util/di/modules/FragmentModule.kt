package com.innovatube.boilerplate.util.di.modules

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class FragmentModule(private val fragment: Fragment) {

    @Provides
    internal fun provideFragment(): Fragment {
        return this.fragment
    }

    @Named("ChildFragmentManager")
    @Provides
    internal fun provideChildFragmentManager(): FragmentManager {
        return fragment.childFragmentManager
    }

    @Named("FragmentManager")
    @Provides
    internal fun provideFragmentManager(): FragmentManager? {
        return fragment.fragmentManager
    }

    @Provides
    internal fun provideLinearLayoutManager(fragment: Fragment): LinearLayoutManager {
        return LinearLayoutManager(fragment.activity)
    }

    @Provides
    internal fun provideActivity(): Activity? {
        return this.fragment.activity
    }
}
