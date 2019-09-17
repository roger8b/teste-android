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
        list.add(ResultBody(InvestmentField.INVESTEDAMOUNT, investedAmount))

        val grossAmount =
            simulateResponse.grossAmount.brazilianMoneyFormat()
        list.add(ResultBody(InvestmentField.GROSSAMOUNT, grossAmount))

        val grossAmountProfit =
            simulateResponse.grossAmountProfit.brazilianMoneyFormat()
        list.add(ResultBody(InvestmentField.GROSSAMOUNTPROFIT, grossAmountProfit))

        val taxesAmount = simulateResponse.taxesAmount.brazilianMoneyFormat()
        val taxesRate = simulateResponse.taxesRate.formatNumberAndAddPercentSignal()
        list.add(ResultBody(InvestmentField.TAXESAMOUNT, "$taxesAmount[$taxesRate]"))

        val netAmount = simulateResponse.netAmount.brazilianMoneyFormat()
        list.add(ResultBody(InvestmentField.NETAMOUNT, netAmount))

        val maturityDate = simulateResponse.investmentParameter.maturityDate.dateFormatDDMMYYYY()
        if (maturityDate == null) throw RuntimeException("Error on parse date $maturityDate")
        list.add(ResultBody(InvestmentField.MATURITYDATE, maturityDate))

        val maturityTotalDays = simulateResponse.investmentParameter.maturityTotalDays.toString()
        list.add(ResultBody(InvestmentField.MATURITYTOTALDAYS, maturityTotalDays))

        val monthlyGrossRateProfit = simulateResponse.monthlyGrossRateProfit.formatNumberAndAddPercentSignal()
        list.add(ResultBody(InvestmentField.MONTHLYGROSSRATEPROFIT, monthlyGrossRateProfit))

        val rate = simulateResponse.investmentParameter.rate.formatNumberAndAddPercentSignal()
        list.add(ResultBody(InvestmentField.RATE, rate))

        val annualNetRateProfit = simulateResponse.annualNetRateProfit.formatNumberAndAddPercentSignal()
        list.add(ResultBody(InvestmentField.ANNUALNETRATEPROFIT, annualNetRateProfit))

        val rateProfit = simulateResponse.rateProfit.formatNumberAndAddPercentSignal()
        list.add(ResultBody(InvestmentField.RATEPROFIT, rateProfit))

        return list
    }

    private fun mapHeader(simulateResponse: SimulateResponse): ResultHeader {
        val grossAmount = simulateResponse.grossAmount.brazilianMoneyFormat()
        val annualGrossRateProfit = simulateResponse.annualGrossRateProfit.brazilianMoneyFormat()

        return ResultHeader(grossAmount, annualGrossRateProfit)
    }
}

