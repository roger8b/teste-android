package br.com.easynvest.calc.data.repository

import br.com.easynvest.calc.data.api.ApiService
import br.com.easynvest.calc.data.model.InvestmentField
import br.com.easynvest.calc.data.model.SimulateRequest
import br.com.easynvest.calc.data.model.SimulateResponse
import br.com.easynvest.calc.entity.BaseResult
import br.com.easynvest.calc.utils.Logs
import io.reactivex.Single
import java.util.PrimitiveIterator

class InvestmentRepository constructor(

    private val apiService: ApiService,
    private val responseMapper: ResponseMapper,
    private val logs: Logs

) : InvestmentRepositoryContract {

    override fun simulate(simulateRequest: SimulateRequest): Single<List<BaseResult>> {
        return apiService.getNews(convertSimulateRequestToMap(simulateRequest))
            .flatMap {
                logs.debug("RECEIVED SIMULATE RESPONSE $it")
                mapResponse(it)
            }
    }

    private fun mapResponse(simulateResponse: SimulateResponse): Single<List<BaseResult>> {
        return responseMapper.mapSimulateResponseToListResult(simulateResponse)
    }

    private fun convertSimulateRequestToMap(simulateRequest: SimulateRequest): Map<String, String> {
        val map = mapOf(
            Pair(InvestmentField.INVESTEDAMOUNT.value, simulateRequest.investedAmount),
            Pair(InvestmentField.INDEX.value, simulateRequest.index),
            Pair(InvestmentField.RATE.value, simulateRequest.rate),
            Pair(InvestmentField.MATURITYDATE.value, simulateRequest.maturityDate),
            Pair(InvestmentField.ISTAXFREE.value, simulateRequest.isTaxFree.toString())
        )
        logs.debug("SIMULATE REQUEST: $map")
        return map
    }
}