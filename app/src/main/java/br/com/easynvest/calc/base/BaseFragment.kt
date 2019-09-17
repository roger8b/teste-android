package br.com.easynvest.calc.base

import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment<T> : Fragment() {

    val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    var listener: T? = null

    override fun onDetach() {
        compositeDisposable.clear()
        super.onDetach()
        listener = null
    }

    fun setVisibilityVisible(view: View) {
        view.visibility = View.VISIBLE
    }

    fun setVisibilityGone(view: View) {
        view.visibility = View.GONE
    }
}
