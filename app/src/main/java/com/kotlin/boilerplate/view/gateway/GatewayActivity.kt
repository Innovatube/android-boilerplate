package com.kotlin.boilerplate.view.gateway

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kotlin.boilerplate.data.prefs.PreferenceHelper
import com.kotlin.boilerplate.data.prefs.PreferenceHelper.get
import com.kotlin.boilerplate.view.example.ExampleActivity

/**
 * Created by anhtuan on 2/27/18.
 */

open class GatewayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isTaskRoot) {
            finish()
            return
        }
        val prefs = PreferenceHelper.defaultPrefs(this)
        val isLogedin: Boolean? = prefs[PreferenceHelper.IS_LOGED_IN]
        if (isLogedin == true) {
        } else {
            startActivity(Intent(this, ExampleActivity::class.java))
        }
        finish()
    }
}