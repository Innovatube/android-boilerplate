package com.kotlin.boilerplate.base

import android.support.v4.app.Fragment
import com.kotlin.boilerplate.R
import com.tapadoo.alerter.Alerter
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by thanhnguyen on 3/8/18.
 */
open class BaseFragment : Fragment() {
    protected val compositeDisposable = CompositeDisposable()

    private fun showAlert(bg: Int, icon: Int, text: String) {
        activity?.let {
            Alerter.create(it)
                    .setTitle(R.string.app_name)
                    .setText(text)
                    .setBackgroundColorRes(bg)
                    .setIcon(icon)
                    .show()
        }
    }

    fun showLoadingNotifi() {
        showLoadingNotifi(getString(R.string.loading))
    }

    fun showLoadingNotifi(text: String) {
        showAlert(R.color.colorAccent, R.drawable.ic_loading, text)
    }

    fun showErrorNotifi(text: String) {
        showAlert(R.color.red, R.drawable.ic_error, text)
    }

    fun showSuccessNotifi(text: String) {
        showAlert(R.color.green, R.drawable.ic_success, text)
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }
}