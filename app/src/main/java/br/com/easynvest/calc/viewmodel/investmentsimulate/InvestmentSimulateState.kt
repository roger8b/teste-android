package br.com.easynvest.calc.viewmodel.investmentsimulate

import br.com.easynvest.calc.entity.BaseResult

sealed class InvestmentSimulateState {
    class ShowResult(val result: List<BaseResult>) : InvestmentSimulateState()
    class ShowError(val error: String) : InvestmentSimulateState()
}