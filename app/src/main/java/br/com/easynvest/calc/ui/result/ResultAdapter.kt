package br.com.easynvest.calc.ui.result

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.easynvest.calc.R
import br.com.easynvest.calc.entity.BaseResult
import br.com.easynvest.calc.entity.ResultBody
import br.com.easynvest.calc.entity.ResultFooter
import br.com.easynvest.calc.entity.ResultHeader
import br.com.easynvest.calc.ext.inflate
import kotlinx.android.synthetic.main.adapter_result_body.view.*
import kotlinx.android.synthetic.main.adapter_result_footer.view.*
import kotlinx.android.synthetic.main.adapter_result_header.view.*

class ResultAdapter(private val list: ArrayList<BaseResult>, private val listener: (() -> Unit)) :
    RecyclerView.Adapter<ResultAdapter.BaseViewHolder>() {

    override fun getItemViewType(position: Int): Int =
        when (list[position]) {
            is ResultHeader -> HEADER
            is ResultBody -> BODY
            is ResultFooter -> FOOTER
            else -> BLANK_LINE
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
            annualGrossRateProfitLabel.text = result.annualGrossRateProfit
        }
    }

    class BodyViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bind(baseResult: BaseResult, listener: () -> Unit) = with(itemView) {
            val result = baseResult as ResultBody
            name.text = result.name
            value.text = result.value
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

const val HEADER = 0
const val BODY = 1
const val FOOTER = 3
const val BLANK_LINE = 4