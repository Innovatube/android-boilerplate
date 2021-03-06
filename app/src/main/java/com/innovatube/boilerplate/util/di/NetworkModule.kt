package com.innovatube.boilerplate.util.di

import android.content.Context
import com.innovatube.boilerplate.BuildConfig
import com.innovatube.boilerplate.R
import com.innovatube.boilerplate.data.api.home.HomeApi
import com.innovatube.boilerplate.data.repository.datasource.storage.StorageMediator
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(storageMediator: StorageMediator): OkHttpClient {
        val client = OkHttpClient().newBuilder()
        client.addInterceptor {

            val original = it.request()

            val builder = original.newBuilder()
            if (storageMediator.isAuthenticated()) {
                builder.header(
                    "Authorization",
                    "Bearer ${storageMediator.getAuthentication()?.accessToken}"
                )
            }
            val request = builder.method(original.method(), original.body())
                .build()
            it.proceed(request)
        }
        if (BuildConfig.DEBUG) {
            client.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun provideHomeApi(context: Context, client: OkHttpClient): HomeApi {
        return createApiClient(
            HomeApi::class.java,
            context.getString(R.string.home_endpoint_url),
            client
        )
    }

    private fun <T> createApiClient(clazz: Class<T>, baseUrl: String, client: OkHttpClient): T {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(clazz)
    }
}
