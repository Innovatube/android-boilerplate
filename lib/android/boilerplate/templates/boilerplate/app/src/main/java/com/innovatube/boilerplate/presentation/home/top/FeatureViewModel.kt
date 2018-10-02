package <%= package_name %>.presentation.home.top

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import <%= package_name %>.domain.model.Article
import <%= package_name %>.domain.usecase.GetFeatureArticlesUseCase
import io.reactivex.functions.Consumer
import timber.log.Timber
import javax.inject.Inject

class FeatureViewModel @Inject constructor(private val useCase: GetFeatureArticlesUseCase) {
    val isLoading = ObservableBoolean()
    val isError = ObservableBoolean()
    val articles: MutableLiveData<List<Article>> = MutableLiveData()
    val onRefresh = MutableLiveData<Boolean>()
    private var featureArticleId: Long = 0

    fun loadArticles(featureArticleId: Long, page: Int = 1) {
        this.featureArticleId = featureArticleId
        isLoading.set(true)
        useCase.execute(
                Consumer {
                    this.articles.value = it
                    this.isLoading.set(false)
                    this.isError.set(false)
                },
                Consumer {
                    Timber.e(it.localizedMessage)
                    this.isLoading.set(false)
                    this.isError.set(true)
                }, GetFeatureArticlesUseCase.Param(featureArticleId, page))
    }

    fun onRefresh() {
        onRefresh.value = true
        this.articles.value = emptyList()
        loadArticles(featureArticleId)
    }

}
