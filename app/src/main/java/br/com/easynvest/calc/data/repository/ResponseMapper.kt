package br.com.easynvest.calc.data.repository

import br.com.easynvest.calc.data.model.InvestmentField
import br.com.easynvest.calc.data.model.SimulateResponse
import br.com.easynvest.calc.entity.BaseResult
import br.com.easynvest.calc.entity.ResultBody
import br.com.easynvest.calc.entity.ResultFooter
import br.com.easynvest.calc.entity.ResultHeader
import br.com.easynvest.calc.ext.brazilianMoneyFormat
import br.com.easynvest.calc.ext.dateFormatDDMMYYYY
import br.com.easynvest.calc.ext.formatNumberAndAddPercentSignal
import io.reactivex.Single

class ResponseMapper {

    fun mapSimulateResponseToListResult(simulateResponse: SimulateResponse): Single<List<BaseResult>> {
        return Single.fromCallable {
            val list = mutableListOf<BaseResult>()
            val header = mapHeader(simulateResponse)
            val bodyList = mapBody(simulateResponse)
            list.add(header)
            list.addAll(bodyList)
            list.add(ResultFooter())
            list
        }
    }

    private fun mapBody(simulateResponse: SimulateResponse): MutableList<ResultBody> {
        val list = mutableListOf<ResultBody>()

        val investedAmount =
            simulateResponse.investmentParameter.investedAmount.brazilianMoneyFormat()
        list.add(ResultBody(InvestmentField.INVESTEDAMOUNT.name, investedAmount))

        val grossAmount =
            simulateResponse.grossAmount.brazilianMoneyFormat()
        list.add(ResultBody(InvestmentField.GROSSAMOUNT.name, grossAmount))

        val grossAmountProfit =
            simulateResponse.grossAmountProfit.brazilianMoneyFormat()
        list.add(ResultBody(InvestmentField.GROSSAMOUNTPROFIT.name, grossAmountProfit))

        val taxesAmount =
            simulateResponse.taxesAmount.brazilianMoneyFormat()
        list.add(ResultBody(InvestmentField.TAXESAMOUNT.name, taxesAmount))

        val netAmountProfit = simulateResponse.netAmountProfit.brazilianMoneyFormat()
        val taxesRate = simulateResponse.taxesRate.formatNumberAndAddPercentSignal()
        list.add(ResultBody(InvestmentField.NETAMOUNT.name, "$netAmountProfit[$taxesRate]"))

        val maturityDate = simulateResponse.investmentParameter.maturityDate.dateFormatDDMMYYYY()
        if (maturityDate == null) throw RuntimeException("Error on parse date $maturityDate")

        list.add(ResultBody(InvestmentField.MATURITYDATE.name, maturityDate))

        val maturityTotalDays =
            simulateResponse.investmentParameter.maturityTotalDays.toString()
        list.add(ResultBody(InvestmentField.MATURITYTOTALDAYS.name, maturityTotalDays))

        val monthlyGrossRateProfit =
            simulateResponse.annualGrossRateProfit.formatNumberAndAddPercentSignal()
        list.add(ResultBody(InvestmentField.MONTHLYGROSSRATEPROFIT.name, monthlyGrossRateProfit))

        val rate = simulateResponse.investmentParameter.rate.formatNumberAndAddPercentSignal()
        list.add(ResultBody(InvestmentField.RATE.name, rate))

        val annualGrossRateProfit =
            simulateResponse.annualGrossRateProfit.formatNumberAndAddPercentSignal()
        list.add(ResultBody(InvestmentField.ANNUALGROSSRATEPROFIT.name, annualGrossRateProfit))

        val rateProfit = simulateResponse.rateProfit.formatNumberAndAddPercentSignal()
        list.add(ResultBody(InvestmentField.RATEPROFIT.name, rateProfit))

        return list
    }

    private fun mapHeader(simulateResponse: SimulateResponse): ResultHeader {
        val grossAmount = simulateResponse.grossAmount.brazilianMoneyFormat()
        val annualGrossRateProfit = simulateResponse.annualGrossRateProfit.brazilianMoneyFormat()

        return ResultHeader(grossAmount, annualGrossRateProfit)
    }
}

