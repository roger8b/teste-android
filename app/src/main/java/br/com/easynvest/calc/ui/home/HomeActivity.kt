package br.com.easynvest.calc.ui.home

import android.app.AlertDialog
import androidx.fragment.app.Fragment
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

    override fun getContentView(): Int = R.layout.activity_home

    override fun onInitViews() {
        supportFragmentManager.beginTransaction()
            .add(R.id.homeContainer, SimulateFormFragment.newInstance())
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
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(getString(R.string.alert_dialog_error_message))
            .setNegativeButton(getString(R.string.alert_dialog_error_button_ok))
            { _, _ -> dialog.setCancelable(true) }
            .create()
            .show()
    }

    private fun showSimulateResult(result: List<BaseResult>) {
        supportFragmentManager.popBackStack()
        val resultFragment = ResultFragment.newInstance()
        resultFragment.fetchResult(result)
        showFragment(resultFragment, RESULT_FRAGMENT)
    }

    override fun onClickButtonNewSimulation() {
        supportFragmentManager.popBackStack()
        showFragment(SimulateFormFragment.newInstance(), SIMULATE_FRAGMENT)
    }

    override fun onClickButtonSimulate(
        investedAmount: String,
        maturityDate: String,
        rate: String,
        index: String,
        taxFree: Boolean
    ) {
        viewModel.fetchSimulation(investedAmount, maturityDate, rate, index, taxFree)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeContainer, fragment)
            .addToBackStack(tag)
            .commit()
    }
}

const val SIMULATE_FRAGMENT = "simulate"
const val RESULT_FRAGMENT = "result"
