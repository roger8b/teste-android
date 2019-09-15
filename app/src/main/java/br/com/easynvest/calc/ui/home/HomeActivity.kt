package br.com.easynvest.calc.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.easynvest.calc.R
import br.com.easynvest.calc.utils.EditTextMask
import br.com.easynvest.calc.utils.UtilsDatePicker
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initInvestedAmount()
        initMaturityInput()
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
                UtilsDatePicker.showDatePicker(inputMaturity, this)
            }
        }

        inputMaturity.setOnClickListener {
            UtilsDatePicker.showDatePicker(inputMaturity, this)
        }
    }
}
