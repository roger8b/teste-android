package br.com.easynvest.calc.di

import br.com.easynvest.calc.data.api.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

}

val mNetworkModules = module {
    single { createNetworkClient(BASE_URL) }
    single { (get() as Retrofit).create(ApiService::class.java) }
}

private const val BASE_URL = "https://api-simulator-calc.easynvest.com.br/calculator/simulate/"
