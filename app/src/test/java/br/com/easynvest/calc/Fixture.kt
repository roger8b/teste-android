package br.com.easynvest.calc

import br.com.easynvest.calc.data.model.InvestmentField
import br.com.easynvest.calc.data.model.InvestmentParameter
import br.com.easynvest.calc.data.model.SimulateRequest
import br.com.easynvest.calc.data.model.SimulateResponse
import br.com.easynvest.calc.entity.ResultBody
import br.com.easynvest.calc.entity.ResultFooter
import br.com.easynvest.calc.entity.ResultHeader

const val grossAmount: Double = 60528.20
const val taxesAmount: Double = 4230.78
const val netAmount: Double = 56297.78
const val grossAmountProfit: Double = 28205.20
const val netAmountProfit: Double = 23974.42
const val annualGrossRateProfit: Double = 87.56
const val monthlyGrossRateProfit: Double = 0.76
const val dailyGrossRateProfit: Double = 0.000445330025305748
const val taxesRate: Double = 15.0
const val rateProfit: Double = 9.5512
const val annualNetRateProfit: Double = 74.17

const val investedAmount: Double = 32323.0
const val yearlyInterestRate: Double = 9.5512
const val maturityTotalDays: Int = 1981
const val maturityBusinessDays: Int = 1409
const val maturityDate: String = "2023-03-03T00:00:00"
const val rate: Double = 123.0
const val isTaxFree: Boolean = false

const val index: String = "CDI"

val validInvestmentParameter = InvestmentParameter(
    investedAmount, yearlyInterestRate,
    maturityTotalDays, maturityBusinessDays, maturityDate, rate, isTaxFree
)

val validInvestmentSimulateResponse = SimulateResponse(
    validInvestmentParameter,
    grossAmount,
    taxesAmount,
    netAmount,
    grossAmountProfit,
    netAmountProfit,
    annualGrossRateProfit,
    monthlyGrossRateProfit,
    dailyGrossRateProfit,
    taxesRate,
    rateProfit,
    annualNetRateProfit
)

val validInvestmentSimulateRequest =
    SimulateRequest(investedAmount.toString(), index, rate.toString(), isTaxFree, maturityDate)

val validResultList = listOf(
    ResultHeader("R$ 60.528,20", "R$ 87,56"),
    ResultBody(InvestmentField.INVESTEDAMOUNT.name, "R$ 32.323,00"),
    ResultBody(InvestmentField.GROSSAMOUNT.name, "R$ 60.528,20"),
    ResultBody(InvestmentField.GROSSAMOUNTPROFIT.name, "R$ 28.205,20"),
    ResultBody(InvestmentField.TAXESAMOUNT.name, "R$ 4.230,78"),
    ResultBody(InvestmentField.NETAMOUNT.name, "R$ 23.974,42[15%]"),
    ResultBody(InvestmentField.MATURITYDATE.name, "03/03/2023"),
    ResultBody(InvestmentField.MATURITYTOTALDAYS.name, "1981"),
    ResultBody(InvestmentField.MONTHLYGROSSRATEPROFIT.name, "87,56%"),
    ResultBody(InvestmentField.RATE.name, "123%"),
    ResultBody(InvestmentField.ANNUALGROSSRATEPROFIT.name, "87,56%"),
    ResultBody(InvestmentField.RATEPROFIT.name, "9,55%"),
    ResultFooter()
)



