package com.innovatube.boilerplate.presentation.base

import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import com.innovatube.boilerplate.InnovatubeApplication
import com.innovatube.boilerplate.util.di.components.ActivityComponent
import com.innovatube.boilerplate.util.di.modules.ActivityModule

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
