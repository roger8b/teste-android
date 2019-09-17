package br.com.easynvest.calc.data.repository

import br.com.easynvest.calc.annualGrossRateProfit
import br.com.easynvest.calc.dailyGrossRateProfit
import br.com.easynvest.calc.data.model.InvestmentParameter
import br.com.easynvest.calc.data.model.SimulateResponse
import br.com.easynvest.calc.entity.BaseResult
import br.com.easynvest.calc.grossAmount
import br.com.easynvest.calc.grossAmountProfit
import br.com.easynvest.calc.investedAmount
import br.com.easynvest.calc.isTaxFree
import br.com.easynvest.calc.maturityBusinessDays
import br.com.easynvest.calc.maturityDateResult
import br.com.easynvest.calc.maturityTotalDays
import br.com.easynvest.calc.monthlyGrossRateProfit
import br.com.easynvest.calc.netAmount
import br.com.easynvest.calc.netAmountProfit
import br.com.easynvest.calc.rate
import br.com.easynvest.calc.rateProfit
import br.com.easynvest.calc.taxesAmount
import br.com.easynvest.calc.taxesRate
import br.com.easynvest.calc.validResultList
import br.com.easynvest.calc.yearlyInterestRate
import com.google.gson.Gson
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.initMocks
import java.lang.RuntimeException

class ResponseMapperTest {

    @Mock lateinit var simulateResponse: SimulateResponse
    @Mock lateinit var investmentParameter : InvestmentParameter

    private lateinit var mapper: ResponseMapper

    @Before
    fun setup() {
        initMocks(this)
        mapper = ResponseMapper()

        `when`(investmentParameter.investedAmount).thenReturn(investedAmount)
        `when`(investmentParameter.yearlyInterestRate).thenReturn(yearlyInterestRate)
        `when`(investmentParameter.maturityTotalDays).thenReturn(maturityTotalDays)
        `when`(investmentParameter.maturityBusinessDays).thenReturn(maturityBusinessDays)
        `when`(investmentParameter.maturityDate).thenReturn(maturityDateResult)
        `when`(investmentParameter.rate).thenReturn(rate)
        `when`(investmentParameter.isTaxFree).thenReturn(isTaxFree)

        `when`(simulateResponse.investmentParameter).thenReturn(investmentParameter)
        `when`(simulateResponse.grossAmount).thenReturn(grossAmount)
        `when`(simulateResponse.taxesAmount).thenReturn(taxesAmount)
        `when`(simulateResponse.netAmount).thenReturn(netAmount)
        `when`(simulateResponse.grossAmountProfit).thenReturn(grossAmountProfit)
        `when`(simulateResponse.netAmountProfit).thenReturn(netAmountProfit)
        `when`(simulateResponse.annualGrossRateProfit).thenReturn(annualGrossRateProfit)
        `when`(simulateResponse.monthlyGrossRateProfit).thenReturn(monthlyGrossRateProfit)
        `when`(simulateResponse.dailyGrossRateProfit).thenReturn(dailyGrossRateProfit)
        `when`(simulateResponse.taxesRate).thenReturn(taxesRate)
        `when`(simulateResponse.rateProfit).thenReturn(rateProfit)
        `when`(simulateResponse.annualNetRateProfit).thenReturn(annualGrossRateProfit)
    }

    @Test
    fun `When you receive valid SimulateResponse you must return a Single with a BaseResult list`() {
        val gson = Gson()
        val testObserver: Single<List<BaseResult>> =
            mapper.mapSimulateResponseToListResult(simulateResponse)

        val values = testObserver.test()
            .assertNoErrors()
            .assertValue {
                val result = gson.toJson(it)
                val expected = gson.toJson(validResultList)
                result == expected
            }.values()
    }

    @Test
    fun `When receiving SimulateResponse with an invalid date, you must return a RuntimeException`() {
        val invalidDate = "00-00-00"
        `when`(simulateResponse.investmentParameter.maturityDate).thenReturn(invalidDate)

        val testObserver: Single<List<BaseResult>> =
            mapper.mapSimulateResponseToListResult(simulateResponse)

        testObserver.test()
            .assertError{
                it is RuntimeException
            }
            //.assertError()



    }
}
