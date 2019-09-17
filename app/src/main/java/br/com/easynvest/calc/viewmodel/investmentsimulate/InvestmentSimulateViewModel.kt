package br.com.easynvest.calc.viewmodel.investmentsimulate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.easynvest.calc.base.BaseSchedulerProvider
import br.com.easynvest.calc.data.model.SimulateRequest
import br.com.easynvest.calc.entity.BaseResult
import br.com.easynvest.calc.usecases.InvestmentSimulateUseCaseContract
import br.com.easynvest.calc.utils.ScreenState
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class InvestmentSimulateViewModel(
    private val useCase: InvestmentSimulateUseCaseContract,
    private val schedulers: BaseSchedulerProvider
) : ViewModel() {

    private val _state: MutableLiveData<ScreenState<InvestmentSimulateState>> = MutableLiveData()

    val state: LiveData<ScreenState<InvestmentSimulateState>>
        get() = _state

    fun fetchSimulation(
        investedAmount: String,
        maturityDate: String,
        rate: String,
        index: String,
        taxFree: Boolean
    ) {
        val simulateRequest = SimulateRequest(investedAmount, index, rate, taxFree, maturityDate)
        useCase.simulate(simulateRequest)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe(object : SingleObserver<List<BaseResult>> {
                override fun onSuccess(t: List<BaseResult>) {
                    _state.value = ScreenState.Render(InvestmentSimulateState.ShowResult(t))
                    _state.value = ScreenState.HideLoading
                }

                override fun onSubscribe(d: Disposable) {
                    _state.value = ScreenState.ShowLoading
                }

                override fun onError(e: Throwable) {
                    val error = e.message ?: ""
                    _state.value = ScreenState.Render(InvestmentSimulateState.ShowError(error))
                    _state.value = ScreenState.HideLoading
                }
            })
    }
}