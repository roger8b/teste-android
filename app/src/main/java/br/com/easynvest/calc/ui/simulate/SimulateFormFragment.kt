package br.com.easynvest.calc.ui.simulate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.easynvest.calc.R
import br.com.easynvest.calc.base.BaseFragment
import br.com.easynvest.calc.utils.EditTextMask
import br.com.easynvest.calc.utils.UtilsDatePicker
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.fragment_simulate_form.*

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
        initSimulate()
    }

    private fun initInvestedAmount() {
        inputInvestedAmount
            .addTextChangedListener(
                EditTextMask.money(inputInvestedAmount)
            )
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

    private fun initSimulate() {

        val investedAmount = getInvestedAmount()
        val maturityDate = getMaturity()
        val rate = getRate()
        val index = "CDI"
        val isTaxFree = false

        val subs = RxTextView.text(inputInvestedAmount)


        buttonSimulate.setOnClickListener {
            listener?.onClickButtonSimulate(
                investedAmount,
                maturityDate,
                rate,
                index,
                isTaxFree
            )
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