package br.com.easynvest.calc.data.repository

import br.com.easynvest.calc.data.api.ApiService
import br.com.easynvest.calc.entity.BaseResult
import br.com.easynvest.calc.ext.anyOfType
import br.com.easynvest.calc.validInvestmentSimulateRequest
import br.com.easynvest.calc.validInvestmentSimulateResponse
import br.com.easynvest.calc.validResultList
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyMap
import org.mockito.MockitoAnnotations.initMocks

class InvestmentRepositoryTest {

    @Mock lateinit var apiService: ApiService
    @Mock lateinit var mapper: ResponseMapper

    private lateinit var repository: InvestmentReposytoryContract

    @Before
    fun setup() {
        initMocks(this)
        repository = InvestmentRepository(apiService, mapper)
    }

    @Test
    fun `When SimulateResponse is valid and the request is successfully made returns a list of Base response`() {
        `when`(apiService.getNews(anyMap())).thenReturn(Single.just(validInvestmentSimulateResponse))

        `when`(mapper.mapSimulateResponseToListResult(anyOfType())).thenReturn(
            Single.just(
                validResultList
            )
        )

        val testObserver: Single<List<BaseResult>> =
            repository.simulate(validInvestmentSimulateRequest)

        testObserver.test()
            .assertNoErrors()
            .assertValue {
                it == validResultList
            }
    }

    @Test
    fun `When valid SimulateResponse and there is a mapping error should return an Exception`() {
        `when`(apiService.getNews(anyMap())).thenReturn(Single.just(validInvestmentSimulateResponse))

        `when`(mapper.mapSimulateResponseToListResult(anyOfType())).thenReturn(
            Single.error(
                RuntimeException()
            )
        )

        val testObserver: Single<List<BaseResult>> =
            repository.simulate(validInvestmentSimulateRequest)

        testObserver.test()
            .assertError {
                it is RuntimeException
            }
       /* testObserver.test()
            .assertValue{
                it == validResultList
            }*/
    }
}