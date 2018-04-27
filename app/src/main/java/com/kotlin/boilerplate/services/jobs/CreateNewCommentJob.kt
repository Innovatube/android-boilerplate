package com.kotlin.boilerplate.services.jobs

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import com.google.gson.Gson
import com.kotlin.boilerplate.model.Comment
import com.kotlin.boilerplate.model.NewComment
import com.kotlin.boilerplate.services.networking.ExampleApiService
import com.kotlin.boilerplate.services.networking.RemoteException
import com.vicpin.krealmextensions.save

/**
 * Created by thanhnguyen on 3/8/18.
 */
class CreateNewCommentJob(val jsonComment: String) : Job(Params(JobPriority.MID)
        .requireNetwork()
        .groupBy(CreateNewCommentJob::class.java.canonicalName)
        .persist()) {

    override fun onAdded() {

    }

    @Throws(Throwable::class)
    override fun onRun() {
        val newComment = Gson().fromJson(jsonComment, NewComment::class.java)
        val comment: Comment? = ExampleApiService.create(applicationContext).createComment(newComment).blockingGet()
        comment?.save()
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