package br.com.easynvest.calc.di

import br.com.easynvest.calc.base.BaseSchedulerProvider
import br.com.easynvest.calc.base.SchedulerProvider
import br.com.easynvest.calc.data.api.ApiService
import br.com.easynvest.calc.data.repository.InvestmentRepository
import br.com.easynvest.calc.data.repository.InvestmentReposytoryContract
import br.com.easynvest.calc.data.repository.ResponseMapper
import br.com.easynvest.calc.viewmodel.investmentsimulate.InvestmentSimulateViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single<InvestmentReposytoryContract> { InvestmentRepository(get(), get()) }
    single<BaseSchedulerProvider> { SchedulerProvider() }
    factory { ResponseMapper() }
}

val viewModel = module {
    viewModel { InvestmentSimulateViewModel(get(), get()) }
}

val mNetworkModules = module {
    single { createNetworkClient(BASE_URL) }
    single { (get() as Retrofit).create(ApiService::class.java) }
}

private const val BASE_URL = "https://api-simulator-calc.easynvest.com.br/calculator/"
