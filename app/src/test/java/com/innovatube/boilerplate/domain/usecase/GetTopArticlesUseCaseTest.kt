package com.innovatube.boilerplate.domain.usecase

import com.innovatube.boilerplate.UnitTest
import com.innovatube.boilerplate.domain.TestSchedulerProvider
import com.innovatube.boilerplate.domain.repository.TopArticleRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class GetTopArticlesUseCaseTest : UnitTest() {
    private lateinit var getTopArticlesUseCase: GetTopArticlesUseCase
    @Mock
    private lateinit var topArticleRepository: TopArticleRepository
    @Mock
    private lateinit var testSchedulerProvider: TestSchedulerProvider

    @Before
    @Throws(Exception::class)
    fun setup() {
        getTopArticlesUseCase = GetTopArticlesUseCase(
            testSchedulerProvider,
            topArticleRepository
        )
    }

    @Test
    fun testGetArticlesFromApiSuccess() {
        getTopArticlesUseCase.buildUseCaseSingle(1)
        Mockito.verify<TopArticleRepository>(topArticleRepository).topArticles(1)
        Mockito.verifyNoMoreInteractions(topArticleRepository)
        Mockito.verifyZeroInteractions(testSchedulerProvider)
    }
}
