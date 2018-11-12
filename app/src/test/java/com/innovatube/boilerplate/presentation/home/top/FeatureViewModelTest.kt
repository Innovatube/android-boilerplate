package com.innovatube.boilerplate.presentation.home.top

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.innovatube.boilerplate.UnitTest
import com.innovatube.boilerplate.capture
import com.innovatube.boilerplate.domain.model.Article
import com.innovatube.boilerplate.domain.usecase.GetFeatureArticlesUseCase
import io.reactivex.functions.Consumer
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito

class FeatureViewModelTest : UnitTest() {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var getFeatureArticlesUseCase: GetFeatureArticlesUseCase
    @Captor
    private lateinit var param: ArgumentCaptor<GetFeatureArticlesUseCase.Param>
    private lateinit var featureViewModel: FeatureViewModel
    @Captor
    private lateinit var callbackOnSuccess: ArgumentCaptor<Consumer<List<Article>>>
    @Captor
    private lateinit var callbackOnError: ArgumentCaptor<Consumer<Throwable>>

    @Before
    fun setUp() {
        featureViewModel = FeatureViewModel(getFeatureArticlesUseCase)
    }

    @Test
    fun getArticles_success() {
        val article = Article(title = "Article title", url = "url")
        featureViewModel.loadArticles(1)
        Mockito.verify(getFeatureArticlesUseCase).execute(capture(callbackOnSuccess), capture(callbackOnError), capture(param))
        Assert.assertTrue(featureViewModel.isLoading.get())
        Assert.assertFalse(featureViewModel.isError.get())
        callbackOnSuccess.value.accept(listOf(article))
        Assert.assertFalse(featureViewModel.isLoading.get())
        Assert.assertFalse(featureViewModel.isError.get())
        Assert.assertNotNull(featureViewModel.articles.value)
        Assert.assertTrue(featureViewModel.articles.value!!.size == 1)
    }

    @Test
    fun getArticles_fail() {
        featureViewModel.loadArticles(1)
        Mockito.verify(getFeatureArticlesUseCase).execute(capture(callbackOnSuccess), capture(callbackOnError), capture(param))
        Assert.assertTrue(featureViewModel.isLoading.get())
        Assert.assertFalse(featureViewModel.isError.get())
        callbackOnError.value.accept(Exception())
        Assert.assertFalse(featureViewModel.isLoading.get())
        Assert.assertTrue(featureViewModel.isError.get())
        Assert.assertNull(featureViewModel.articles.value)
    }
}