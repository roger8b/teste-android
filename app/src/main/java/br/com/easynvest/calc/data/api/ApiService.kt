package br.com.easynvest.calc.data.api

import br.com.easynvest.calc.data.model.SimulateResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("simulate")
    fun getNews(@QueryMap options: Map<String, Any>): Single<SimulateResponse>
}
