package br.com.easynvest.calc.ui.result

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import br.com.easynvest.calc.R
import br.com.easynvest.calc.base.BaseFragment
import br.com.easynvest.calc.entity.BaseResult
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : BaseFragment<ResultFragment.Listener>() {

    private var resultAdapter: ResultAdapter? = null
    private var resultList: MutableList<BaseResult> = mutableListOf()

    companion object {
        fun newInstance() = ResultFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    fun fetchResult(list: List<BaseResult>) {
        resultList.addAll(list)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultAdapter = ResultAdapter(resultList) {
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
