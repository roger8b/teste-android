package br.com.easynvest.calc.data.model

data class InvestmentParameter (
    val investedAmount: Double,
    val yearlyInterestRate: Double,
    val maturityTotalDays: Int,
    val maturityBusinessDays: Int,
    val maturityDate: String,
    val rate: Double,
    val isTaxFree: Boolean
)
