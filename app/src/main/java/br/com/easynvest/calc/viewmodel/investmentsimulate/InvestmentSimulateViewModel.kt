package br.com.easynvest.calc.viewmodel.investmentsimulate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.easynvest.calc.data.model.SimulateRequest
import br.com.easynvest.calc.data.repository.InvestmentReposytoryContract
import br.com.easynvest.calc.entity.BaseResult
import br.com.easynvest.calc.ext.dateFormatYYYMMDD
import br.com.easynvest.calc.ext.removeBranzilianMoneyFormat
import br.com.easynvest.calc.utils.ScreenState
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class InvestmentSimulateViewModel(
    private val repository: InvestmentReposytoryContract
) : ViewModel() {

    private val _state: MutableLiveData<ScreenState<InvestmentSimulateState>> = MutableLiveData()

    val state: LiveData<ScreenState<InvestmentSimulateState>>
        get() = _state

    fun fetchSimulation(
        investedAmount: String,
        maturityDate: String,
        rate: String
    ) {
        val simulateRequest = SimulateRequest(
            investedAmount.removeBranzilianMoneyFormat(),
            "CDI",
            rate,
            false,
            maturityDate.dateFormatYYYMMDD()?: ""
        )

        repository.simulate(simulateRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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