package br.com.easynvest.calc.entity

import br.com.easynvest.calc.data.model.InvestmentField

data class ResultBody(
    val name: InvestmentField,
    val value: String
) : BaseResult