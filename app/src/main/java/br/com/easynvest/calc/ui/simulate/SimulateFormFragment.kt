package br.com.easynvest.calc.ui.simulate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.easynvest.calc.R
import br.com.easynvest.calc.base.BaseFragment
import br.com.easynvest.calc.ext.addMoneyMask
import br.com.easynvest.calc.ext.addPercentMask
import br.com.easynvest.calc.ext.dateFormatYYYMMDD
import br.com.easynvest.calc.ext.removeBranzilianMoneyFormat
import br.com.easynvest.calc.ext.removePercentFormat
import br.com.easynvest.calc.utils.UtilsDatePicker
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.functions.Function3
import kotlinx.android.synthetic.main.fragment_simulate_form.*
import java.util.Locale

class SimulateFormFragment : BaseFragment<SimulateFormFragment.Listener>() {

    companion object {
        fun newInstance() = SimulateFormFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_simulate_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initInvestedAmount()
        initMaturityInput()
        initRateInput()
        initSimulate()
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.dispose()
    }

    private fun initInvestedAmount() {
        val addMoneyMask = inputInvestedAmount.addMoneyMask(Locale("pt", "BR"))
        if (addMoneyMask != null) {
            compositeDisposable.add(addMoneyMask)
        }
    }

    private fun initMaturityInput() {
        inputMaturity.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                context?.let { UtilsDatePicker.showDatePicker(inputMaturity, it) }
            }
        }

        inputMaturity.setOnClickListener {
            context?.let { it1 -> UtilsDatePicker.showDatePicker(inputMaturity, it1) }
        }
    }

    private fun initRateInput() {
        val addPercentMask = inputRate.addPercentMask()
        if (addPercentMask != null) {
            compositeDisposable.add(addPercentMask)
        }
    }

    private fun initSimulate() {
        initObservingFormFill()
        buttonSimulate.setOnClickListener {
            extractFormData()
        }
    }

    private fun extractFormData() {
        val investedAmount = getInvestedAmount().removeBranzilianMoneyFormat()
        val maturityDate = getMaturity().dateFormatYYYMMDD() ?: ""
        val rate = getRate().removePercentFormat()
        val index = "CDI"
        val isTaxFree = false

        listener?.onClickButtonSimulate(
            investedAmount,
            maturityDate,
            rate,
            index,
            isTaxFree
        )
    }

    private fun initObservingFormFill() {
        val investedAmountObservable =
            RxTextView.textChanges(inputInvestedAmount).map { it.isNotEmpty() }
        val maturityObservable = RxTextView.textChanges(inputMaturity).map { it.isNotEmpty() }
        val rateObservable = RxTextView.textChanges(inputRate).map { it.isNotEmpty() }

        val isSignInEnabled: Observable<Boolean> = Observable.combineLatest(
            investedAmountObservable,
            maturityObservable,
            rateObservable,
            Function3 { t1, t2, t3 -> t1 && t2 && t3 })
        compositeDisposable.add(
            isSignInEnabled.subscribe { enableButton(it) }
        )
    }

    private fun enableButton(isEnable: Boolean) {
        if(buttonSimulate != null)
        if (isEnable) {
            setVisibilityVisible(buttonSimulate)
        } else {
            setVisibilityGone(buttonSimulate)
        }
    }

    private fun getRate() = inputRate.text.toString()

    private fun getMaturity() = inputMaturity.text.toString()

    private fun getInvestedAmount() = inputInvestedAmount.text.toString()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener) {
            listener = context
        } else {
            throw RuntimeException("$context listener not implemented")
        }
    }

    interface Listener {
        fun onClickButtonSimulate(
            investedAmount: String,
            maturityDate: String,
            rate: String,
            index: String,
            taxFree: Boolean
        )
    }
}