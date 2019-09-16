package br.com.easynvest.calc.data.repository

import br.com.easynvest.calc.data.model.SimulateResponse
import br.com.easynvest.calc.data.model.SimulateRequest
import br.com.easynvest.calc.entity.BaseResult
import io.reactivex.Single

interface InvestmentReposytoryContract {

    fun simulate(simulateRequest : SimulateRequest): Single<List<BaseResult>>
}
