package com.freevision.ibs.services.jobs

import android.content.Context

class JobManagerInitializer {

    fun initialize(context: Context) {
        JobManagerFactory.getJobManager(context)
    }
}
