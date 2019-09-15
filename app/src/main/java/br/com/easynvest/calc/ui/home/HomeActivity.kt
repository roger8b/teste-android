package br.com.easynvest.calc.ui.home

import br.com.easynvest.calc.R
import br.com.easynvest.calc.base.BaseActivity
import br.com.easynvest.calc.ui.result.ResultFragment
import br.com.easynvest.calc.ui.simulate.SimulateFormFragment

class HomeActivity : BaseActivity(), ResultFragment.Listener, SimulateFormFragment.Listener {

    private val simulateFormFragment: SimulateFormFragment by lazy {
        SimulateFormFragment.newInstance()
    }

    private val resultFragment: ResultFragment by lazy {
        ResultFragment.newInstance()
    }

    override fun getContentView(): Int = R.layout.activity_home

    override fun onInitViews() {
        supportFragmentManager.beginTransaction()
            .add(R.id.homeContainer, simulateFormFragment)
            .addToBackStack(SIMULATE_FRAGMENT)
            .commit()
    }

    override fun onClickButtonNewSimulation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeContainer, simulateFormFragment)
            .addToBackStack(SIMULATE_FRAGMENT)
            .commit()
    }

    override fun onClickButtonSimulate() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeContainer, resultFragment)
            .addToBackStack(RESULT_FRAGMENT)
            .commit()
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
