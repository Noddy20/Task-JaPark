package com.demo.japark.network

import com.demo.japark.models.data.ModelMainResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface RetrofitApiClient {

    @GET(ApiEndPoints.GET_MAIN_RESPONSE)
    fun getMainDataAsync(): Deferred<ModelMainResponse?>

}