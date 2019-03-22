package com.innovatube.boilerplate

import android.app.Application
import android.content.Context
import com.innovatube.boilerplate.presentation.base.BaseActivity
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Base class for Android tests. Inherit from it to create test cases which contain android
 * framework dependencies or components.
 *
 * @see UnitTest
 */
@RunWith(RobolectricTestRunner::class)
@Config(
    application = AndroidTest.ApplicationStub::class,
    sdk = [21]
)
abstract class AndroidTest {
    fun context(): Context = RuntimeEnvironment.application
    fun activityContext(): Context = mock(BaseActivity::class.java)
    internal class ApplicationStub : Application()
}
