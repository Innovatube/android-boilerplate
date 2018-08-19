package com.innovatube.boilerplate.domain.usecase

import com.innovatube.boilerplate.UnitTest
import com.innovatube.boilerplate.domain.TestSchedulerProvider
import com.innovatube.boilerplate.domain.repository.HeaderRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class GetHeadersUseCaseTest : UnitTest() {
    private lateinit var getHeadersUseCase: GetHeadersUseCase
    @Mock
    private lateinit var headerRepository: HeaderRepository
    @Mock
    private lateinit var testSchedulerProvider: TestSchedulerProvider


    @Before
    @Throws(Exception::class)
    fun setup() {
        getHeadersUseCase = GetHeadersUseCase(testSchedulerProvider, headerRepository)
    }


    @Test
    fun testGetHeadersSuccess() {
        getHeadersUseCase.buildUseCaseFlowable(null)
        Mockito.verify<HeaderRepository>(headerRepository).headers()
        Mockito.verifyNoMoreInteractions(headerRepository)
        Mockito.verifyZeroInteractions(testSchedulerProvider)

    }


}