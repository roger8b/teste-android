package br.com.easynvest.calc.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        onInitViews()
    }

    abstract fun onInitViews()

    abstract fun getContentView(): Int
}
