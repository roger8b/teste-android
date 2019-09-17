package br.com.easynvest.calc.usecases

import br.com.easynvest.calc.data.model.SimulateRequest
import br.com.easynvest.calc.data.repository.InvestmentRepositoryContract
import br.com.easynvest.calc.entity.BaseResult
import io.reactivex.Single

class InvestmentSimulateUseCase(private val repository: InvestmentRepositoryContract) :
    InvestmentSimulateUseCaseContract {
    override fun simulate(simulateRequest: SimulateRequest): Single<List<BaseResult>> {
        return repository.simulate(simulateRequest)
    }
}