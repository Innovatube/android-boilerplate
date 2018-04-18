package com.kotlin.boilerplate.data

import android.content.SharedPreferences
import com.kotlin.boilerplate.data.prefs.PreferenceHelper
import com.kotlin.boilerplate.data.prefs.PreferenceHelper.set
import com.kotlin.boilerplate.model.Comment
import com.kotlin.boilerplate.model.NewComment
import com.kotlin.boilerplate.model.Post
import com.kotlin.boilerplate.services.networking.ExampleApiService
import com.vicpin.krealmextensions.Query
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryAsFlowable
import com.vicpin.krealmextensions.queryFirst
import io.reactivex.Flowable
import io.reactivex.Single
import io.realm.RealmObject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by anhtuan on 3/2/18.
 */
@Singleton
open class DataManager
@Inject
constructor(private val prefs: SharedPreferences, private val exampleApi: ExampleApiService) {
    fun saveToken(token: String) {
        prefs[PreferenceHelper.TOKEN] = token
        prefs[PreferenceHelper.IS_LOGED_IN] = true
    }

    fun clearSession() {
        prefs[PreferenceHelper.TOKEN] = null
        prefs[PreferenceHelper.IS_LOGED_IN] = false
    }

    fun <T : RealmObject> getObjectLocal(clazz: Class<T>, id: String): T? {
        return clazz.newInstance()
                .queryFirst { equalTo("id", id) }
    }

    fun <T : RealmObject> getObjectLocal(clazz: Class<T>, id: Int): T? {
        return clazz.newInstance()
                .queryFirst { equalTo("id", id) }
    }

    fun <T : RealmObject> getObjectsLocal(clazz: Class<T>, query: Query<T>): List<T> {
        return clazz.newInstance()
                .query(query)
    }

    fun <T : RealmObject> observeObjectLocal(clazz: Class<T>, id: String): Flowable<T> {
        return clazz.newInstance()
                .queryAsFlowable { equalTo("id", id) }
                .filter { it.isNotEmpty() }
                .map { it.first() }
    }

    fun <T : RealmObject> observeObjectLocal(clazz: Class<T>, id: Int): Flowable<T> {
        return clazz.newInstance()
                .queryAsFlowable { equalTo("id", id) }
                .filter { it.isNotEmpty() }
                .map { it.first() }
    }

    fun <T : RealmObject> observeObjectsLocal(clazz: Class<T>, query: Query<T>): Flowable<List<T>> {
        return clazz.newInstance()
                .queryAsFlowable(query = query)
    }

    fun getPosts(): Single<List<Post>> {
        return exampleApi.getPosts()
    }

    fun getComments(postId: Int): Single<List<Comment>> {
        return exampleApi.getComments(postId)
    }

    fun createComment(comment: NewComment): Single<Comment> {
        return exampleApi.createComment(comment)
    }
}