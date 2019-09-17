package br.com.easynvest.calc.ui.result

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.easynvest.calc.R
import br.com.easynvest.calc.data.model.InvestmentField.ANNUALNETRATEPROFIT
import br.com.easynvest.calc.data.model.InvestmentField.GROSSAMOUNT
import br.com.easynvest.calc.data.model.InvestmentField.GROSSAMOUNTPROFIT
import br.com.easynvest.calc.data.model.InvestmentField.INVESTEDAMOUNT
import br.com.easynvest.calc.data.model.InvestmentField.MATURITYDATE
import br.com.easynvest.calc.data.model.InvestmentField.MATURITYTOTALDAYS
import br.com.easynvest.calc.data.model.InvestmentField.MONTHLYGROSSRATEPROFIT
import br.com.easynvest.calc.data.model.InvestmentField.NETAMOUNT
import br.com.easynvest.calc.data.model.InvestmentField.RATE
import br.com.easynvest.calc.data.model.InvestmentField.RATEPROFIT
import br.com.easynvest.calc.data.model.InvestmentField.TAXESAMOUNT
import br.com.easynvest.calc.entity.BaseResult
import br.com.easynvest.calc.entity.ResultBody
import br.com.easynvest.calc.entity.ResultFooter
import br.com.easynvest.calc.entity.ResultHeader
import br.com.easynvest.calc.ext.inflate
import kotlinx.android.synthetic.main.adapter_result_body.view.*
import kotlinx.android.synthetic.main.adapter_result_footer.view.*
import kotlinx.android.synthetic.main.adapter_result_header.view.*

class ResultAdapter(private val list: List<BaseResult>, private val listener: (() -> Unit)) :
    RecyclerView.Adapter<ResultAdapter.BaseViewHolder>() {

    override fun getItemViewType(position: Int): Int =
        when (list[position]) {
            is ResultHeader -> HEADER
            is ResultBody -> BODY
            is ResultFooter -> FOOTER
            else -> 0
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (viewType) {
            HEADER -> HeaderViewHolder(parent.inflate(R.layout.adapter_result_header))
            BODY -> BodyViewHolder(parent.inflate(R.layout.adapter_result_body))
            FOOTER -> FooterViewHolder(parent.inflate(R.layout.adapter_result_footer))
            else -> throw IllegalArgumentException("View type not found")
        }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun getItemCount(): Int = list.size

    class HeaderViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bind(baseResult: BaseResult, listener: () -> Unit) = with(itemView) {
            val result = baseResult as ResultHeader
            grossAmount.text = result.grossAmount
            annualGrossRateProfitLabel.text = context.getString(
                R.string.result_adapter_header_annualGrossRateProfit,
                result.annualGrossRateProfit
            )
        }
    }

    class BodyViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bind(baseResult: BaseResult, listener: () -> Unit) = with(itemView) {
            val result = baseResult as ResultBody
            value.text = result.value
            name.text = when (result.name) {
                INVESTEDAMOUNT -> context.getString(R.string.result_adapter_investedAmount_label)
                GROSSAMOUNT -> context.getString(R.string.result_adapter_grossAmount_label)
                GROSSAMOUNTPROFIT -> context.getString(R.string.result_adapter_grossAmountProfit_label)
                TAXESAMOUNT -> context.getString(R.string.result_adapter_taxesAmount_label)
                NETAMOUNT -> context.getString(R.string.result_adapter_net_amount_label)
                MATURITYDATE -> context.getString(R.string.result_adapter_maturityDate_label)
                MATURITYTOTALDAYS -> context.getString(R.string.result_adapter_maturityTotalDays_label)
                MONTHLYGROSSRATEPROFIT -> context.getString(R.string.result_adapter_monthlyGrossRateProfit_label)
                RATE -> context.getString(R.string.result_adapter_rate_label)
                ANNUALNETRATEPROFIT -> context.getString(R.string.result_adapter_annual_net_rate_profit_label)
                RATEPROFIT -> context.getString(R.string.result_adapter_rate_profit_label)
                else -> ""
            }
        }
    }

    class FooterViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bind(baseResult: BaseResult, listener: () -> Unit) = with(itemView) {
            buttonNewSimulation.setOnClickListener {
                listener()
            }
        }
    }

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        abstract fun bind(baseResult: BaseResult, listener: (() -> Unit))
    }
}

const val HEADER = 1
const val BODY = 2
const val FOOTER = 3
