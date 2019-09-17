package br.com.easynvest.calc.usecases

import br.com.easynvest.calc.data.model.SimulateRequest
import br.com.easynvest.calc.data.repository.InvestmentRepository
import br.com.easynvest.calc.entity.BaseResult
import br.com.easynvest.calc.ext.anyOfType
import br.com.easynvest.calc.index
import br.com.easynvest.calc.investedAmount
import br.com.easynvest.calc.isTaxFree
import br.com.easynvest.calc.maturityDateRequest
import br.com.easynvest.calc.rate
import br.com.easynvest.calc.validInvestmentSimulateRequest
import br.com.easynvest.calc.validResultList
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.MockitoAnnotations.initMocks

class InvestmentSimulateUseCaseTest {

    @Mock
    lateinit var repository: InvestmentRepository
    @Mock
    lateinit var simulateRequest: SimulateRequest
    lateinit var useCase: InvestmentSimulateUseCase

    @Before
    fun setup() {
        initMocks(this)
        useCase = InvestmentSimulateUseCase(repository)
        `when`(simulateRequest.investedAmount).thenReturn(investedAmount.toString())
        `when`(simulateRequest.index).thenReturn(index)
        `when`(simulateRequest.rate).thenReturn(rate.toString())
        `when`(simulateRequest.isTaxFree).thenReturn(isTaxFree)
        `when`(simulateRequest.maturityDate).thenReturn(maturityDateRequest)
    }

    @Test
    fun `When all SimulateRequest fields are normalized without error the simulate method should return a list of BaseResult`(){

        `when`(repository.simulate(anyOfType())).thenReturn(
            Single.just(
                validResultList
            )
        )

        val testObserver: Single<List<BaseResult>> =
            repository.simulate(validInvestmentSimulateRequest)

        testObserver.test()
            .assertNoErrors()
            .assertValue{
                it == validResultList
            }
    }

    @Test
    fun `When an error occurs in data simulate conversion should return a RuntimeException`(){
        `when`(simulateRequest.maturityDate).thenReturn("")

        `when`(repository.simulate(anyOfType())).thenReturn(
            Single.just(
                validResultList
            )
        )

        val testObserver: Single<List<BaseResult>> =
            useCase.simulate(validInvestmentSimulateRequest)


        testObserver.test()
            .assertError {
                it is RuntimeException
            }
    }
}