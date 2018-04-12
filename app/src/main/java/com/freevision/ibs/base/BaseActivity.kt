package com.freevision.ibs.base

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.kotlin.boilerplate.R
import com.tapadoo.alerter.Alerter
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by anhtuan on 3/6/18.
 */
abstract class BaseActivity : AppCompatActivity() {
    protected val compositeDisposable = CompositeDisposable()

    val DATA_ID = "data_id"

    val ARGUMENTS = "arguments"

    private fun showAlert(bg: Int, icon: Int, text: String) {
        Alerter.create(this)
                .setBackgroundColorRes(bg)
                .setTitle(R.string.app_name)
                .setText(text)
                .setIcon(icon)
                .show()
    }

    protected fun showLoadingNotifi(text: String) {
        showAlert(R.color.colorAccent, R.drawable.ic_loading, text)
    }

    protected fun showLoadingNotifi() {
        showLoadingNotifi(getString(R.string.loading))
    }

    protected fun showErrorNotifi(text: String) {
        showAlert(R.color.red, R.drawable.ic_error, text)
    }

    protected fun showSuccessNotifi(text: String) {
        showAlert(R.color.green, R.drawable.ic_success, text)
    }

    protected fun getDataId(): Int {
        return intent.getIntExtra(DATA_ID, -1)
    }

    protected fun getArguments(): ArrayList<Int> {
        return intent.getIntegerArrayListExtra(ARGUMENTS)
    }

    protected fun startActivity(c: Class<*>) {
        startActivity(Intent(baseContext, c))
    }

    protected fun startActivityWithDataId(c: Class<*>, id: Int) {
        val i = Intent(baseContext, c)
        i.putExtra(DATA_ID, id)
        startActivity(i)
    }

    protected fun startActivityWithIntArguments(c: Class<*>, arguments: ArrayList<Int>) {
        val i = Intent(baseContext, c)
        i.putIntegerArrayListExtra(ARGUMENTS, arguments)
        startActivity(i)
    }

    protected fun startActivityForResult(c: Class<*>, code: Int) {
        startActivityForResult(Intent(baseContext, c), code)
    }

    protected fun startActivityForResultWithDataId(c: Class<*>, code: Int, id: Int) {
        val i = Intent(baseContext, c)
        i.putExtra(DATA_ID, id)
        startActivityForResult(i, code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_CANCELED && data != null) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}