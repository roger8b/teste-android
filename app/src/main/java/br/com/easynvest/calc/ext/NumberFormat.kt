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


