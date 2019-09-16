package br.com.easynvest.calc.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        onInitViews()
    }

    abstract fun onInitViews()

    abstract fun getContentView(): Int

    fun setVisibilityVisible(view: View) {
        view.visibility = View.VISIBLE
    }

    fun setVisibilityGone(view: View) {
        view.visibility = View.GONE
    }
}
