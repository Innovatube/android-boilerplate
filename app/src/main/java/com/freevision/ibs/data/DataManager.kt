package com.freevision.ibs.data

import android.content.SharedPreferences
import com.freevision.ibs.data.prefs.PreferenceHelper
import com.freevision.ibs.data.prefs.PreferenceHelper.set
import com.freevision.ibs.services.networking.IbsApiService
import com.vicpin.krealmextensions.Query
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryAsFlowable
import com.vicpin.krealmextensions.queryFirst
import io.reactivex.Flowable
import io.realm.RealmObject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by anhtuan on 3/2/18.
 */
@Singleton
open class DataManager
@Inject
constructor(private val prefs: SharedPreferences, private val ibsApi: IbsApiService) {
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
}