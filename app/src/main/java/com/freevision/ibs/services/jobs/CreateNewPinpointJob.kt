package com.freevision.ibs.services.jobs

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import com.freevision.ibs.services.networking.IbsApiService
import com.freevision.ibs.services.networking.RemoteException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by thanhnguyen on 3/8/18.
 */
class CreateNewPinpointJob(val pinpoint: String, val createdAt: Int) : Job(Params(JobPriority.MID)
        .requireNetwork()
        .groupBy(CreateNewPinpointJob::class.java.canonicalName)
        .persist()) {

    override fun onAdded() {

    }

    @Throws(Throwable::class)
    override fun onRun() {
    }

    override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        if (throwable is RemoteException) {
            val statusCode = throwable.response.code()
            if (statusCode in 400..499) {
                return RetryConstraint.CANCEL
            }
        }
        // if we are here, most likely the connection was lost during job execution
        return RetryConstraint.RETRY
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {

    }
}