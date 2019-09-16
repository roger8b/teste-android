package br.com.easynvest.calc.ext

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

fun String.dateFormatDDMMYYYY() = parseDate(
    this,
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US),
    SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
)

private fun parseDate(
    inputDateString: String, input: SimpleDateFormat, output: SimpleDateFormat
): String? =
    try {
        val parse = input.parse(inputDateString)
        if (parse != null) output.format(parse) else throw  RuntimeException("Input date format error")
    } catch (e: ParseException) {
        throw RuntimeException("Error on parse date")
    }