package br.com.easynvest.calc.ext

import android.widget.EditText
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.RxTextView.textChanges
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

fun EditText.addMoneyMask(locale: Locale): Disposable? {
    return textChanges(this)
        .skip(1)
        .debounce(800, TimeUnit.MILLISECONDS)
        .observeOn(Schedulers.computation())
        .filter {
            it.isNotEmpty()
        }
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe { t ->
            val cleanString = t.toString().replace(Regex("[R$,.\\s]"), "")
            if (cleanString.isNotEmpty()) {

                val parsed = convertToBigDecimalAndRound(cleanString)

                val current = NumberFormat.getCurrencyInstance(locale).format(parsed)

                if (parsed > BigDecimal.ZERO) {
                    setText(current)
                    setSelection(current.length)
                } else {
                    setText("")
                }
            } else {
                setText("")
            }
        }
}

fun EditText.addPercentMask(): Disposable? {
    return textChanges(this)
        .skip(1)
        .debounce(800, TimeUnit.MILLISECONDS)
        .observeOn(Schedulers.computation())
        .filter {
            it.isNotEmpty()
        }
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { t ->
            val cleanString = t.toString().replace(Regex("[%,.\\s]"), "")
            if (cleanString.isNotEmpty()) {

                val parsed = convertToBigDecimalAndRound(cleanString)

                val current = DecimalFormat("###.##%").format(parsed)

                if (parsed > BigDecimal.ZERO) {
                    setText(current)
                    setSelection(current.length - 1)
                } else {
                    setText("")
                }
            } else {
                setText("")
            }
        }
}

private fun convertToBigDecimalAndRound(cleanString: String): BigDecimal = try {
    BigDecimal(cleanString)
        .setScale(2, BigDecimal.ROUND_FLOOR)
        .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)
} catch (e: NumberFormatException) {
    BigDecimal.ZERO
}