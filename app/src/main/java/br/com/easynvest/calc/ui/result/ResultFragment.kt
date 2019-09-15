package br.com.easynvest.calc.ui.result

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import br.com.easynvest.calc.R
import br.com.easynvest.calc.base.BaseFragment
import br.com.easynvest.calc.entity.ResultBody
import br.com.easynvest.calc.entity.ResultFooter
import br.com.easynvest.calc.entity.ResultHeader
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : BaseFragment<ResultFragment.Listener>() {

    var resultAdapter: ResultAdapter? = null

    companion object {
        fun newInstance() = ResultFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resultAdapter = ResultAdapter(
            arrayListOf(

                ResultHeader(
                    "R$ 1.088,69",
                    "Rendimento total de R$88,69"
                ),

                ResultBody("Valor aplicado inicialmente", "R$1000,00"),
                ResultBody("Valor bruto do investimento", "R$1000,00"),
                ResultBody("Valor do investimento", "R$1000,00"),
                ResultBody("IR sobre o investimento", "R$1000,00"),
                ResultBody("Valor líquido do investimento", "R$1000,00"),

                ResultBody("", ""),
                ResultBody("", ""),

                ResultBody("Data de resgate", "27/20/2018"),
                ResultBody("Dias corridos", "365"),
                ResultBody("Rendimento mensal", "0,57%"),
                ResultBody("Percentual do CDI do investimento", "123%"),
                ResultBody("Rentabilidade anual", "7,09%"),
                ResultBody("Rentabilidade do período", "8,87%"),

                ResultFooter()
            )
        ) {
            listener?.onClickButtonNewSimulation()
        }

        simulateResultList.run {
            layoutManager = GridLayoutManager(context, 1)
            adapter = resultAdapter
            setHasFixedSize(true)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener) {
            listener = context
        } else {
            throw RuntimeException("$context listener not implemented")
        }
    }

    interface Listener {
        fun onClickButtonNewSimulation()
    }
}
