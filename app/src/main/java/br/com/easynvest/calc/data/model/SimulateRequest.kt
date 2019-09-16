package br.com.easynvest.calc.data.model

data class SimulateRequest (
    val investedAmount: String,
    val index: String,
    val rate: String,
    val isTaxFree: Boolean,
    val maturityDate: String
)