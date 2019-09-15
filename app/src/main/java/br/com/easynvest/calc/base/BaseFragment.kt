package br.com.easynvest.calc.base

import androidx.fragment.app.Fragment

open class BaseFragment<T> : Fragment() {

    var listener: T? = null

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
