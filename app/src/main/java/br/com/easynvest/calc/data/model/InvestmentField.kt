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

    @SerializedName("netAmountProfit")
    NETAMOUNTPROFIT("netAmountProfit"),

    @SerializedName("annualGrossRateProfit")
    ANNUALGROSSRATEPROFIT("annualGrossRateProfit"),

    @SerializedName("monthlyGrossRateProfit")
    MONTHLYGROSSRATEPROFIT("monthlyGrossRateProfit"),

    @SerializedName("dailyGrossRateProfit")
    DAILYGROSSRATEPROFIT("dailyGrossRateProfit"),

    @SerializedName("taxesRate")
    TAXESRATE("taxesRate"),

    @SerializedName("rateProfit")
    RATEPROFIT("rateProfit"),

    @SerializedName("annualNetRateProfit")
    ANNUALNETRATEPROFIT("annualNetRateProfit"),

    @SerializedName("investedAmount")
    INVESTEDAMOUNT("investedAmount"),

    @SerializedName("yearlyInterestRate")
    YEARLYINTERESTRATE("yearlyInterestRate"),

    @SerializedName("maturityTotalDays")
    MATURITYTOTALDAYS("maturityTotalDays"),

    @SerializedName("maturityBusinessDays")
    MATURITYBUSINESSDAYS("maturityBusinessDays"),

    @SerializedName("maturityDate")
    MATURITYDATE("maturityDate"),

    @SerializedName("rate")
    RATE("rate"),

    @SerializedName("isTaxFree")
    ISTAXFREE("isTaxFree"),

    @SerializedName("index")
    INDEX("index"),

}