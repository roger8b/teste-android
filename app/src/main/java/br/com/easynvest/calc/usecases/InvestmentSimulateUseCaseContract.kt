package br.com.easynvest.calc.usecases

import br.com.easynvest.calc.data.model.SimulateRequest
import br.com.easynvest.calc.entity.BaseResult
import io.reactivex.Single

interface InvestmentSimulateUseCaseContract {
    fun simulate(simulateRequest: SimulateRequest): Single<List<BaseResult>>
}
