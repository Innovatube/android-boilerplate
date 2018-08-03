package com.innovatube.boilerplate.presentation.home.adapter

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.innovatube.boilerplate.AppExecutors
import com.innovatube.boilerplate.BR
import com.innovatube.boilerplate.R
import com.innovatube.boilerplate.databinding.ItemHomeBinding
import com.innovatube.boilerplate.domain.model.Article
import com.innovatube.boilerplate.presentation.base.DataBoundListAdapter


class FeatureArticleAdapter(
        appExecutors: AppExecutors,
        private val onItemClick: ((Article) -> Unit)?
) : DataBoundListAdapter<Article, ItemHomeBinding>(
        appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

        }
) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ItemHomeBinding {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DataBindingUtil.inflate(layoutInflater, R.layout.item_home, parent, false)
    }

    override fun bind(binding: ItemHomeBinding, item: Article) {
        binding.setVariable(BR.article, item)
        binding.root.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }
}