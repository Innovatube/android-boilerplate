package com.kotlin.boilerplate.view.example

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.kotlin.boilerplate.base.BaseActivity
import com.kotlin.boilerplate.presentation.ExampleViewModel
import com.kotlin.boilerplate.presentation.ExampleViewModelFactory
import com.kotlin.boilerplate.R
import dagger.android.AndroidInjection
import javax.inject.Inject

class ExampleActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ExampleViewModelFactory
    private lateinit var viewModel: ExampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExampleViewModel::class.java)
    }
}
