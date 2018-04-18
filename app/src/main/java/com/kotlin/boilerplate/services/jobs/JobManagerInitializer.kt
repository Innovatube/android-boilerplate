package com.kotlin.boilerplate.services.jobs

import android.content.Context

class JobManagerInitializer {

    fun initialize(context: Context) {
        JobManagerFactory.getJobManager(context)
    }
}
