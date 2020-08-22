package com.mockdroid.koran.network

import com.mockdroid.koran.model.TopHeadlineResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Routes {

    @GET("v2/top-headlines")
    fun getTopHeadline(
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String?
    ): Call<TopHeadlineResponse>
}