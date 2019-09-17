package br.com.easynvest.calc.data.model

import com.google.gson.annotations.SerializedName

enum class InvestmentField( val value : String) {

    @SerializedName("grossAmount")
    GROSSAMOUNT("grossAmount"),

    @SerializedName("taxesAmount")
    TAXESAMOUNT("taxesAmount"),

    @SerializedName("netAmount")
    NETAMOUNT("netAmount"),

    @SerializedName("grossAmountProfit")
    GROSSAMOUNTPROFIT("grossAmountProfit"),

    @SerializedName("annualGrossRateProfit")
    ANNUALGROSSRATEPROFIT("annualGrossRateProfit"),

    @SerializedName("monthlyGrossRateProfit")
    MONTHLYGROSSRATEPROFIT("monthlyGrossRateProfit"),

    @SerializedName("rateProfit")
    RATEPROFIT("rateProfit"),

    @SerializedName("annualNetRateProfit")
    ANNUALNETRATEPROFIT("annualNetRateProfit"),

    @SerializedName("investedAmount")
    INVESTEDAMOUNT("investedAmount"),

    @SerializedName("maturityTotalDays")
    MATURITYTOTALDAYS("maturityTotalDays"),

    @SerializedName("maturityDate")
    MATURITYDATE("maturityDate"),

    @SerializedName("rate")
    RATE("rate"),

    @SerializedName("isTaxFree")
    ISTAXFREE("isTaxFree"),

    @SerializedName("index")
    INDEX("index"),

    @SerializedName("blank_line")
    BLANK_LINE("blan_line"),

}