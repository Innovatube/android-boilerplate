package <%= package_name %>.presentation.home.top

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import <%= package_name %>.AppExecutors
import <%= package_name %>.BR
import <%= package_name %>.R
import <%= package_name %>.databinding.ItemHomeBinding
import <%= package_name %>.databinding.ItemHomeHeaderBinding
import <%= package_name %>.domain.model.Article
import <%= package_name %>.presentation.base.DataBoundListAdapter

class TopRecyclerViewAdapter(
        appExecutors: AppExecutors,
        private val onItemClick: ((Article) -> Unit)?
) : DataBoundListAdapter<Article, ViewDataBinding>(
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
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val layoutInflater = LayoutInflater.from(parent.context)
        val isHeader = viewType == Type.HEADER.value
        val layoutType = if (isHeader) R.layout.item_home_header else R.layout.item_home

        return DataBindingUtil.inflate(layoutInflater, layoutType, parent, false)
    }

    override fun bind(binding: ViewDataBinding, item: Article) {
        binding.setVariable(BR.article, item)
        when (binding) {
            is ItemHomeHeaderBinding -> {
                binding.rlContainer.setOnClickListener { onItemClick?.invoke(item) }
            }
            is ItemHomeBinding -> {
                binding.root.setOnClickListener { onItemClick?.invoke(item) }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) Type.HEADER.value else Type.ITEM.value
    }

    enum class Type(val value: Int) {
        HEADER(0),
        ITEM(1)
    }
}