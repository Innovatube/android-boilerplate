package com.kotlin.boilerplate.presentation

import com.kotlin.boilerplate.base.BaseViewModel
import com.kotlin.boilerplate.data.DataManager
import com.kotlin.boilerplate.model.Comment
import com.kotlin.boilerplate.model.NewComment
import com.kotlin.boilerplate.model.Post
import com.vicpin.krealmextensions.save
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by anhtuan on 3/6/18.
 */
class ExampleViewModel(val dataManager: DataManager) : BaseViewModel() {
    fun observerPosts(): Flowable<List<Post>> {
        dataManager.getPosts()
                .subscribeBy(onError = {
                    it.printStackTrace()
                }).addTo(disposables)
        return dataManager.observeObjectsLocal(Post::class.java, {})
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun observerComments(postId: Int): Flowable<List<Comment>> {
        dataManager.getComments(postId)
                .subscribeBy(onError = {
                    it.printStackTrace()
                }).addTo(disposables)
        return dataManager.observeObjectsLocal(Comment::class.java, { equalTo("postId", postId) })
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun createComment(postId: Int, comment: String): Completable {
        val newComment = NewComment()
        newComment.name = "some user"
        newComment.email = "random@mail.com"
        newComment.postId = postId
        newComment.body = comment
        return dataManager.createComment(newComment)
                .flatMapCompletable { Completable.fromAction { it.save() } }
                .observeOn(AndroidSchedulers.mainThread())
    }
}