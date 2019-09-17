package br.com.easynvest.calc.viewmodel.investmentsimulate

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import br.com.easynvest.calc.base.BaseSchedulerProvider
import br.com.easynvest.calc.base.TrampolineSchedulerProvider
import br.com.easynvest.calc.data.repository.InvestmentRepository
import br.com.easynvest.calc.ext.anyOfType
import br.com.easynvest.calc.index
import br.com.easynvest.calc.investedAmount
import br.com.easynvest.calc.isTaxFree
import br.com.easynvest.calc.maturityDateRequest
import br.com.easynvest.calc.rate
import br.com.easynvest.calc.utils.ScreenState
import br.com.easynvest.calc.validResultList
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import kotlin.test.assertEquals

class InvestmentSimulateViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: InvestmentRepository

    @Mock
    lateinit var viewModel: InvestmentSimulateViewModel
    @Mock
    lateinit var observer: Observer<ScreenState<InvestmentSimulateState>>

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner
    lateinit var lifecycle: Lifecycle
    lateinit var schedulerProvider: BaseSchedulerProvider

    @Before
    fun setup() {
        initMocks(this)
        schedulerProvider = TrampolineSchedulerProvider()
        viewModel = InvestmentSimulateViewModel(repository, schedulerProvider)
        lifecycle = LifecycleRegistry(lifecycleOwner)
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `When fetchSimulation is called, the ShowLoading status should be sent to view and when the request terminate the HideLoading status should be sent`() {
        `when`(repository.simulate(anyOfType())).thenReturn(Single.just(validResultList))
        viewModel.fetchSimulation(
            investedAmount.toString(),
            maturityDateRequest,
            rate.toString(),
            index,
            isTaxFree
        )
        verify(observer, times(1)).onChanged(ScreenState.ShowLoading)
        verify(observer, times(1)).onChanged(ScreenState.HideLoading)

    }

    @Test
    fun `When fetchSimulation is called, and the request is successfully completed ShowResult state should be sent to view with a BaseResult list`() {
        `when`(repository.simulate(anyOfType())).thenReturn(Single.just(validResultList))
        viewModel.fetchSimulation(
            investedAmount.toString(),
            maturityDateRequest,
            rate.toString(),
            index,
            isTaxFree
        )

        val value = viewModel.state.value
        if(value is ScreenState.Render){
            assertEquals(value.renderState, InvestmentSimulateState.ShowResult(validResultList))
        }
    }

    @Test
    fun `When fetchSimulation is called, and the request fails to send a state ShowError and HideLoading`() {
        `when`(repository.simulate(anyOfType())).thenReturn(Single.error(Throwable("")))
        viewModel.fetchSimulation(
            investedAmount.toString(),
            maturityDateRequest,
            rate.toString(),
            index,
            isTaxFree
        )

        val value = viewModel.state.value
        if(value is ScreenState.Render){
            assertEquals(value.renderState, InvestmentSimulateState.ShowError(""))
        }
        verify(observer, times(1)).onChanged(ScreenState.HideLoading)

    }
}