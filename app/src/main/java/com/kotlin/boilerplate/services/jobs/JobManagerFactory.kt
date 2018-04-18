package com.kotlin.boilerplate.services.jobs

import android.content.Context
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration
import com.birbit.android.jobqueue.log.CustomLogger
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService
import timber.log.Timber

object JobManagerFactory {

    @get:Synchronized
    var jobManager: JobManager? = null
        private set

    private val customLogger = object : CustomLogger {

        override fun isDebugEnabled(): Boolean {
            return true
        }

        override fun d(text: String, vararg args: Any) {
            Timber.d(String.format(text, *args))
        }

        override fun e(t: Throwable, text: String, vararg args: Any) {
            Timber.e(t, String.format(text, *args))
        }

        override fun e(text: String, vararg args: Any) {
            Timber.e(String.format(text, *args))
        }

        override fun v(text: String, vararg args: Any) {
            // no-op
        }
    }

    @Synchronized
    fun getJobManager(context: Context): JobManager {
        if (jobManager == null) {
            jobManager = configureJobManager(context)
        }
        return jobManager as JobManager
    }

    private fun configureJobManager(context: Context): JobManager {
        val builder = Configuration.Builder(context)
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minutes
                .customLogger(customLogger)

        // we are setting batch param below to false so that sync results are observed and acted upon in the background.
        builder.scheduler(FrameworkJobSchedulerService.createSchedulerFor(context, SchedulerJobService::class.java), false)
        return JobManager(builder.build())
    }
}
