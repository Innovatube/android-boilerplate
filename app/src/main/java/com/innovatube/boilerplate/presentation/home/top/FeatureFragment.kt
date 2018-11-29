package com.innovatube.boilerplate.presentation.home.top

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innovatube.boilerplate.AppExecutors
import com.innovatube.boilerplate.databinding.FragmentFeatureBinding
import com.innovatube.boilerplate.domain.model.Header
import com.innovatube.boilerplate.presentation.base.BaseFragment
import com.innovatube.boilerplate.presentation.helper.DialogHelper
import com.innovatube.boilerplate.presentation.helper.Navigator
import com.innovatube.boilerplate.presentation.home.adapter.FeatureArticleAdapter
import com.innovatube.boilerplate.presentation.listener.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.fragment_feature.*
import javax.inject.Inject

class FeatureFragment : BaseFragment() {

    private lateinit var binding: FragmentFeatureBinding
    @Inject
    lateinit var viewModel: FeatureViewModel
    @Inject
    lateinit var layoutManager: LinearLayoutManager
    @Inject
    lateinit var appExecutors: AppExecutors
    private var adapter: FeatureArticleAdapter? = null
    private lateinit var header: Header
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var dialogHelper: DialogHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        header = arguments!!.getParcelable(ARGS_HEADER) ?: return
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        component.inject(this)
        binding = FragmentFeatureBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvArticle.layoutManager = layoutManager
        if (adapter == null) {
            adapter = FeatureArticleAdapter(
                appExecutors
            ) {
            }
        }
        rvArticle.adapter = adapter

        val scrollListener = object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(currentPage: Int) {
                viewModel.loadArticles(header.featureId, currentPage)
            }
        }
        rvArticle.addOnScrollListener(scrollListener)

        viewModel.onRefresh.observe(this, Observer {
            it?.let { scrollListener.reset() }
        })
        viewModel.loadArticles(header.featureId)
        viewModel.articles.observe(this, Observer {
            it?.let {
                adapter?.submitList(it)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RQ_QUOTE_ARTICLE && resultCode == Activity.RESULT_OK) {
        }
    }

    companion object {
        private const val ARGS_HEADER = "args_header"
        private const val RQ_QUOTE_ARTICLE = 0x1

        fun newInstance(header: Header): FeatureFragment {
            val fragment = FeatureFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARGS_HEADER, header)
            fragment.arguments = bundle
            return fragment
        }
    }
}
