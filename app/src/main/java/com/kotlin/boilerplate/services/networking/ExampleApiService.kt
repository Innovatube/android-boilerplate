package com.kotlin.boilerplate.services.networking

import android.content.Context
import android.os.Build
import com.kotlin.boilerplate.data.prefs.PreferenceHelper
import com.kotlin.boilerplate.data.prefs.PreferenceHelper.get
import com.google.gson.GsonBuilder
import com.kotlin.boilerplate.BuildConfig
import com.kotlin.boilerplate.model.Comment
import com.kotlin.boilerplate.model.NewComment
import com.kotlin.boilerplate.model.Post
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.realm.RealmList
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

/**
 * Created by anhtuan on 3/5/18.
 */

interface ExampleApiService {
    @GET("posts")
    fun getPosts(): Single<List<Post>>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") id: Int): Single<List<Comment>>

    @POST("comments")
    fun createComment(@Body comment: NewComment): Single<Comment>

    companion object Creator {
        fun create(context: Context): ExampleApiService {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            }
            val tokenInterceptor = Interceptor {

                val builder = it.request().newBuilder()
                        .header("Platform", "Android")
                        .header("VersionCode", BuildConfig.VERSION_CODE.toString())
                        .header("VersionName", BuildConfig.VERSION_NAME)
                        .header("DeviceName", Build.MODEL)
                        .header("Os", Build.VERSION.RELEASE)
                if (it.request().headers()["NoAuth"] == null) {
                    val token = PreferenceHelper.defaultPrefs(context)[PreferenceHelper.TOKEN] ?: ""
                    builder.header("Authorization", "Bearer $token")
                }
                it.proceed(builder.build())
            }

            val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(tokenInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build()
            val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:SSS'Z'")
                    .create()
            var baseUrl = BuildConfig.API_BASE_URL
            if (!baseUrl.endsWith("/")) baseUrl = "$baseUrl/"

            return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(okHttpClient)
                    .build()
                    .create(ExampleApiService::class.java)
        }
    }
}