package br.com.easynvest.calc.ext

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

fun Double.formatNumberAndAddPercentSignal(): String {
    val decimalFormat = DecimalFormat("###.##%")
    return decimalFormat.format(this / 100)
}

fun Double.brazilianMoneyFormat(): String =
    NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this)

fun String.removeBranzilianMoneyFormat(): String {
    val stringValue = this.replace(Regex("[R$,.\\s]"), "")
    return (stringValue.toDouble() / 100).toString()
}

fun String.removePercentFormat(): String = this.replace(Regex("[%,.\\s]"), "")


