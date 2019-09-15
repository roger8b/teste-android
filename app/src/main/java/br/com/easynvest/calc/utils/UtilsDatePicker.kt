package br.com.easynvest.calc.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import java.util.Calendar

object UtilsDatePicker {

    fun showDatePicker(input: EditText, context: Context) {
        val calendar = Calendar.getInstance()
        val currentTime = calendar.timeInMillis
        val dialog = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { _, year, mouth, day ->
                input.setText(
                    StringBuilder()
                        .append(day)
                        .append("/")
                        .append(mouth)
                        .append("/")
                        .append(year)
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.datePicker.minDate = currentTime
        dialog.show()
    }
}