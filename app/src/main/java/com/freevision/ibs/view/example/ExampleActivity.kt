package com.freevision.ibs.view.example

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.freevision.ibs.base.BaseActivity
import com.freevision.ibs.presentation.ExampleViewModel
import com.freevision.ibs.presentation.ExampleViewModelFactory
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
        setContentView(R.layout.activity_forgot_password)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExampleViewModel::class.java)
    }
}
