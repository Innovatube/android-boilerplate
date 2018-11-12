package com.innovatube.boilerplate.data.repository

import com.innovatube.boilerplate.UnitTest
import com.innovatube.boilerplate.data.api.common.entity.LinksEntity
import com.innovatube.boilerplate.data.api.home.HomeApi
import com.innovatube.boilerplate.data.api.home.entity.ArticleEntity
import com.innovatube.boilerplate.data.api.home.entity.ArticlesEntity
import com.innovatube.boilerplate.data.mapper.ArticleMapper
import com.innovatube.boilerplate.domain.model.Article
import io.reactivex.Single
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.*

class FeatureArticleDataRepositoryTest : UnitTest() {

    private lateinit var featureArticleDataRepository: FeatureArticleDataRepository
    @Spy
    private lateinit var mapper: ArticleMapper
    @Mock
    private lateinit var homeApi: HomeApi
    @Mock
    private lateinit var linkEntity: LinksEntity
    @Mock
    private lateinit var articlesEntity: ArticlesEntity

    @Before
    fun setUp() {
        featureArticleDataRepository = FeatureArticleDataRepository(homeApi, mapper)
    }

    @Test
    fun featureArticles_callFromApi() {
        BDDMockito.given(homeApi.getFeatureArticles(BDDMockito.anyLong(), ArgumentMatchers.anyInt())).willReturn(Single.just(articlesEntity))
        featureArticleDataRepository.featureArticles(1, 1)
        Mockito.verify<HomeApi>(homeApi).getFeatureArticles(1, 1)
    }

    @Test
    fun featureArticles_complete() {
        BDDMockito.given(homeApi.getFeatureArticles(BDDMockito.anyLong(), ArgumentMatchers.anyInt())).willReturn(Single.just(articlesEntity))
        val testObserver = featureArticleDataRepository.featureArticles(1, 1).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertComplete()
    }

    @Test
    fun featureArticles_fail() {
        val response = Throwable("Error productEntity")
        BDDMockito.given(homeApi.getFeatureArticles(BDDMockito.anyLong(), ArgumentMatchers.anyInt())).willReturn(Single.error(response))
        val testObserver = featureArticleDataRepository.featureArticles(1, 1).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertError(response)
    }

    @Test
    fun featureArticles_success() {
        val article = Article(title = "test title", url = "url")
        val articleEntity = ArticleEntity(title = "test title", url = "url")
        val articlesEntity = ArticlesEntity(linkEntity, listOf(articleEntity), 1, 1, 1)
        BDDMockito.given(homeApi.getFeatureArticles(1, 1)).willReturn(Single.just(articlesEntity))
        val testObserver = featureArticleDataRepository.featureArticles(1, 1).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValues(listOf(article))

        Assert.assertEquals(testObserver.values()[0][0].title, "test title")
        Assert.assertEquals(testObserver.values()[0][0].url, "url")

        Mockito.verify(homeApi).getFeatureArticles(1, 1)
        Mockito.verify(mapper).transform(articleEntity)
    }
}