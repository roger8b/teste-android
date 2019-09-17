package br.com.easynvest.calc.usecases

import br.com.easynvest.calc.data.model.SimulateRequest
import br.com.easynvest.calc.data.repository.InvestmentRepositoryContract
import br.com.easynvest.calc.entity.BaseResult
import br.com.easynvest.calc.ext.dateFormatYYYMMDD
import br.com.easynvest.calc.ext.removeBranzilianMoneyFormat
import br.com.easynvest.calc.ext.removePercentFormat
import io.reactivex.Single

class InvestmentSimulateUseCase(private val repository: InvestmentRepositoryContract) :
    InvestmentSimulateUseCaseContract {

    override fun simulate(simulateRequest: SimulateRequest): Single<List<BaseResult>> {
        return normaliseSimulateRequestData(simulateRequest).flatMap {
            repository.simulate(it)
        }
    }

    private fun normaliseSimulateRequestData(simulateRequest: SimulateRequest): Single<SimulateRequest> {
        return Single.fromCallable {
            val investedAmount = simulateRequest.investedAmount.removeBranzilianMoneyFormat()
            val maturityDate = simulateRequest.maturityDate.dateFormatYYYMMDD()
            val rate = simulateRequest.rate.removePercentFormat()
            val index = simulateRequest.index
            val taxFree = simulateRequest.isTaxFree

            if(maturityDate != null){
                SimulateRequest(
                    investedAmount,
                    index,
                    rate,
                    taxFree,
                    maturityDate
                )
            } else throw RuntimeException("Error on Maturity Date normalise")
        }
    }
}