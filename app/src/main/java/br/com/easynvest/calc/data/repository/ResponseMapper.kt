package br.com.easynvest.calc.data.repository

import br.com.easynvest.calc.data.model.InvestmentField
import br.com.easynvest.calc.data.model.SimulateResponse
import br.com.easynvest.calc.entity.BaseResult
import br.com.easynvest.calc.entity.ResultBody
import br.com.easynvest.calc.entity.ResultFooter
import br.com.easynvest.calc.entity.ResultHeader
import io.reactivex.Single
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

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
            brazilianMoneyFormat(simulateResponse.investmentParameter.investedAmount)
        list.add(ResultBody(InvestmentField.INVESTEDAMOUNT.name, investedAmount))

        val grossAmount =
            brazilianMoneyFormat(simulateResponse.grossAmount)
        list.add(ResultBody(InvestmentField.GROSSAMOUNT.name, grossAmount))

        val grossAmountProfit =
            brazilianMoneyFormat(simulateResponse.grossAmountProfit)
        list.add(ResultBody(InvestmentField.GROSSAMOUNTPROFIT.name, grossAmountProfit))

        val taxesAmount =
            brazilianMoneyFormat(simulateResponse.taxesAmount)
        list.add(ResultBody(InvestmentField.TAXESAMOUNT.name, taxesAmount))

        val netAmountProfit =
            brazilianMoneyFormat(simulateResponse.netAmountProfit)
        val taxesRate =
            percentNumberFormat(simulateResponse.taxesRate)
        list.add(ResultBody(InvestmentField.NETAMOUNT.name, netAmountProfit + taxesRate))

        val maturityDate = parseDate(
            simulateResponse.investmentParameter.maturityDate,
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US),
            SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        )
        if (maturityDate == null) throw RuntimeException("Error on parse date $maturityDate")

        list.add(ResultBody(InvestmentField.MATURITYDATE.name, maturityDate))

        val maturityTotalDays =
            simulateResponse.investmentParameter.maturityTotalDays.toString()
        list.add(ResultBody(InvestmentField.MATURITYTOTALDAYS.name, maturityTotalDays))

        val monthlyGrossRateProfit =
            percentNumberFormat(simulateResponse.annualGrossRateProfit)
        list.add(
            ResultBody(
                InvestmentField.MONTHLYGROSSRATEPROFIT.name,
                monthlyGrossRateProfit
            )
        )

        val rate =
            percentNumberFormat(simulateResponse.investmentParameter.rate)
        list.add(ResultBody(InvestmentField.RATE.name, rate))

        val annualGrossRateProfit =
            percentNumberFormat(simulateResponse.annualGrossRateProfit)
        list.add(ResultBody(InvestmentField.ANNUALGROSSRATEPROFIT.name, annualGrossRateProfit))

        val rateProfit =
            percentNumberFormat(simulateResponse.rateProfit)
        list.add(ResultBody(InvestmentField.RATEPROFIT.name, rateProfit))

        return list
    }

    private fun mapHeader(simulateResponse: SimulateResponse): ResultHeader {
        val grossAmount = brazilianMoneyFormat(simulateResponse.grossAmount)
        val annualGrossRateProfit = brazilianMoneyFormat(simulateResponse.annualGrossRateProfit)

        return ResultHeader(grossAmount, annualGrossRateProfit)
    }

    private fun brazilianMoneyFormat(parsed: Double): String =
        NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(parsed)

    private fun parseDate(
        inputDateString: String, input: SimpleDateFormat, output: SimpleDateFormat
    ): String? =
        try {
            val parse = input.parse(inputDateString)
            if (parse != null) output.format(parse) else throw  RuntimeException("Input date format error")
        } catch (e: ParseException) {
            throw RuntimeException("Error on parse date")
        }

    private fun percentNumberFormat(parsed: Double): String = "$parsed%"
}

