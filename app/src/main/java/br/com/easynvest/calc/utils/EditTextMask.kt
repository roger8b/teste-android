package br.com.easynvest.calc.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

object EditTextMask {

    fun money(editText: EditText): TextWatcher {
        var listener: TextWatcher? = null
        listener = object : TextWatcher {
            var current: String = ""
            override fun onTextChanged(sequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (sequence.toString() != current) {
                    editText.removeTextChangedListener(listener)

                    val cleanString = sequence.toString().replace(Regex("[R$,.]"), "")
                    val parsed = BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR)
                        .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)

                    current = if (parsed > BigDecimal.ZERO)
                        NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(parsed)
                    else
                        ""
                    editText.run {
                        setText(current)
                        setSelection(current.length)
                        addTextChangedListener(listener)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                /* Not Implemented */
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                /* Not Implemented */
            }
        }

        return listener
    }
}
