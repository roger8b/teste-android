package br.com.easynvest.calc.utils

import timber.log.Timber

class Logs {

    private val tag = "CALC_INVEST"

    fun debug(message: String) {
        Timber.tag(tag).d(message)
    }

    fun error(message: String, e: Throwable) {
        Timber.tag(tag).e(e, message)
    }
}