package br.com.easynvest.calc.di

import br.com.easynvest.calc.base.BaseSchedulerProvider
import br.com.easynvest.calc.base.SchedulerProvider
import br.com.easynvest.calc.data.api.ApiService
import br.com.easynvest.calc.data.repository.InvestmentRepository
import br.com.easynvest.calc.data.repository.InvestmentRepositoryContract
import br.com.easynvest.calc.data.repository.ResponseMapper
import br.com.easynvest.calc.usecases.InvestmentSimulateUseCase
import br.com.easynvest.calc.usecases.InvestmentSimulateUseCaseContract
import br.com.easynvest.calc.utils.Logs
import br.com.easynvest.calc.viewmodel.investmentsimulate.InvestmentSimulateViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single<InvestmentSimulateUseCaseContract> { InvestmentSimulateUseCase(get()) }
    single<InvestmentRepositoryContract> { InvestmentRepository(get(), get(), get()) }
    single<BaseSchedulerProvider> { SchedulerProvider() }
    factory { ResponseMapper(get()) }
    single { Logs() }
}

val viewModel = module {
    viewModel { InvestmentSimulateViewModel(get(), get(), get()) }
}

val mNetworkModules = module {
    single { createNetworkClient(BASE_URL) }
    single { (get() as Retrofit).create(ApiService::class.java) }
}

private const val BASE_URL = "https://api-simulator-calc.easynvest.com.br/calculator/"
