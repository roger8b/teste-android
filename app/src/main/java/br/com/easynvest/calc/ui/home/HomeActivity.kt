package br.com.easynvest.calc.ui.home

import android.util.Log
import br.com.easynvest.calc.R
import br.com.easynvest.calc.base.BaseActivity
import br.com.easynvest.calc.entity.BaseResult
import br.com.easynvest.calc.ui.result.ResultFragment
import br.com.easynvest.calc.ui.simulate.SimulateFormFragment
import br.com.easynvest.calc.utils.ScreenState
import br.com.easynvest.calc.viewmodel.investmentsimulate.InvestmentSimulateState
import br.com.easynvest.calc.viewmodel.investmentsimulate.InvestmentSimulateViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity(), ResultFragment.Listener, SimulateFormFragment.Listener {

    private val viewModel: InvestmentSimulateViewModel by viewModel()

    private val simulateFormFragment: SimulateFormFragment by lazy {
        SimulateFormFragment.newInstance()
    }

    override fun getContentView(): Int = R.layout.activity_home

    override fun onInitViews() {
        supportFragmentManager.beginTransaction()
            .add(R.id.homeContainer, simulateFormFragment)
            .addToBackStack(SIMULATE_FRAGMENT)
            .commit()

        viewModel.state.observe(::getLifecycle, ::updateUI)
    }

    private fun updateUI(screenState: ScreenState<InvestmentSimulateState>?) {
        when (screenState) {
            is ScreenState.ShowLoading -> setVisibilityVisible(progress)
            is ScreenState.HideLoading -> setVisibilityGone(progress)
            is ScreenState.Render -> processRenderState(screenState.renderState)
        }
    }

    private fun processRenderState(renderState: InvestmentSimulateState) {
        when (renderState) {
            is InvestmentSimulateState.ShowResult -> showSimulateResult(renderState.result)
            is InvestmentSimulateState.ShowError -> showErrorMessage(renderState.error)
        }
    }

    private fun showErrorMessage(error: String) {
        Log.e("Home", error)
    }

    private fun showSimulateResult(result: List<BaseResult>) {
        val resultFragment = ResultFragment.newInstance()
        resultFragment.fetchResult(result)
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeContainer, resultFragment)
            .addToBackStack(RESULT_FRAGMENT)
            .commit()
    }

    override fun onClickButtonNewSimulation() {
        supportFragmentManager.popBackStack()
    }

    override fun onClickButtonSimulate(
        investedAmount: String,
        maturityDate: String,
        rate: String,
        index: String,
        taxFree: Boolean
    ) {
        viewModel.fetchSimulation(investedAmount, maturityDate, rate)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}

const val SIMULATE_FRAGMENT = "simulate"
const val RESULT_FRAGMENT = "result"
