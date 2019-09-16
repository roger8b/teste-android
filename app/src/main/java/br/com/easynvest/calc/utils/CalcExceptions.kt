package br.com.easynvest.calc.utils

sealed class CalcExceptions constructor(

    override val message: String

) : RuntimeException() {

}