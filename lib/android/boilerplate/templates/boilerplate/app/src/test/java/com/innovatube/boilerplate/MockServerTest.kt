package <%= package_name %>

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Base class for Unit tests. Inherit from it to create test cases which DO NOT contain android
 * framework dependencies or components.
 *
 * @see AndroidTest
 */

abstract class MockServerTest : UnitTest() {

    protected fun <T> createApiClient(clazz: Class<T>, baseUrl: String): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(clazz)
    }
}
