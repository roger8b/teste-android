package br.com.easynvest.calc.data.repository

import br.com.easynvest.calc.data.api.ApiService
import br.com.easynvest.calc.data.model.InvestmentField
import br.com.easynvest.calc.data.model.SimulateRequest
import br.com.easynvest.calc.data.model.SimulateResponse
import br.com.easynvest.calc.entity.BaseResult
import io.reactivex.Single

class InvestmentRepository constructor(

    private val apiService: ApiService,
    private val responseMapper: ResponseMapper

) : InvestmentReposytoryContract {

    override fun simulate(simulateRequest: SimulateRequest): Single<List<BaseResult>> {
        return apiService.getNews(convertSimulateRequestToMap(simulateRequest))
            .flatMap {
                mapResponse(it)
            }
    }

    private fun mapResponse(simulateResponse: SimulateResponse): Single<List<BaseResult>> {
        return responseMapper.mapSimulateResponseToListResult(simulateResponse)
    }

    private fun convertSimulateRequestToMap(simulateRequest: SimulateRequest): Map<String, String> {

        return mapOf(
            Pair(InvestmentField.INVESTEDAMOUNT.value, simulateRequest.investedAmount),
            Pair(InvestmentField.INDEX.value, simulateRequest.index),
            Pair(InvestmentField.RATE.value, simulateRequest.rate),
            Pair(InvestmentField.ISTAXFREE.value, simulateRequest.isTaxFree.toString())
        )
    }
}