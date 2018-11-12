package com.innovatube.boilerplate.domain.usecase

import com.innovatube.boilerplate.UnitTest
import com.innovatube.boilerplate.data.repository.FeatureArticleDataRepository
import com.innovatube.boilerplate.domain.TestSchedulerProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class GetFeatureArticlesUseCaseTest : UnitTest() {

    private lateinit var getFeatureArticlesUseCase: GetFeatureArticlesUseCase
    @Mock
    private lateinit var featureArticleRepository: FeatureArticleDataRepository
    @Mock
    private lateinit var testSchedulerProvider: TestSchedulerProvider

    @Before
    @Throws(Exception::class)
    fun setup() {
        getFeatureArticlesUseCase = GetFeatureArticlesUseCase(
                testSchedulerProvider,
                featureArticleRepository)
    }

    @Test
    fun testGetArticlesFromApiSuccess() {
        getFeatureArticlesUseCase.buildUseCaseSingle(GetFeatureArticlesUseCase.Param(1, 1))
        Mockito.verify<FeatureArticleDataRepository>(featureArticleRepository).featureArticles(1, 1)
        Mockito.verifyNoMoreInteractions(featureArticleRepository)
        Mockito.verifyZeroInteractions(testSchedulerProvider)
    }
}