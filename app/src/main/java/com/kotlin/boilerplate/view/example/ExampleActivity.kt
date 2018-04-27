package com.kotlin.boilerplate.view.example

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.kotlin.boilerplate.R
import com.kotlin.boilerplate.base.BaseActivity
import com.kotlin.boilerplate.presentation.ExampleViewModel
import com.kotlin.boilerplate.presentation.ExampleViewModelFactory
import dagger.android.AndroidInjection
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.android.synthetic.main.row_post.view.*
import kotlinx.android.synthetic.main.pop_up_comment.view.*
import kotlinx.android.synthetic.main.row_comment.view.*
import javax.inject.Inject

class ExampleActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ExampleViewModelFactory
    private lateinit var viewModel: ExampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        listPost.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExampleViewModel::class.java)
        viewModel.observerPosts()
                .subscribeBy(onNext = {
                    listPost.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                            return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false)) {}
                        }

                        override fun getItemCount(): Int {
                            return it.size
                        }

                        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                            holder.itemView.tvTitle.text = it[position].title
                            holder.itemView.tvPostBody.text = it[position].body
                            holder.itemView.setOnClickListener { _ ->
                                showComments(it[position].id)
                            }
                        }

                    }
                }, onError = {
                    it.printStackTrace()
                }).addTo(compositeDisposable)
    }

    fun showComments(postId: Int) {
        val popup = MaterialDialog.Builder(this)
                .title(R.string.comment)
                .customView(R.layout.pop_up_comment, false)
                .build()
        val root = popup.customView
        val listComment = root?.listComment
        listComment?.layoutManager = LinearLayoutManager(this)
        root?.btnSend?.setOnClickListener {
            val comment = root.edt?.text.toString()
            if (comment.isNotEmpty()) {
                viewModel.createComment(postId, comment)
                        .subscribeBy(onComplete = {
                            root.edt.text.clear()
                            listComment?.smoothScrollToPosition((listComment.adapter?.itemCount ?: 0) - 1)
                        }, onError = {
                            it.printStackTrace()
                        }).addTo(compositeDisposable)
            }
        }
        popup.show()
        viewModel.observerComments(postId)
                .subscribeBy(onNext = {
                    listComment?.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                            return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_comment, parent, false)) {}
                        }

                        override fun getItemCount(): Int {
                            return it.size
                        }

                        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                            holder.itemView.tvName.text = it[position].name
                            holder.itemView.tvEmail.text = it[position].email
                            holder.itemView.tvCommentBody.text = it[position].body
                        }
                    }

                }, onError = {
                    it.printStackTrace()
                }).addTo(compositeDisposable)
    }
}
