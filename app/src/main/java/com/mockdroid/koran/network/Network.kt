package com.mockdroid.koran.network

import com.mockdroid.koran.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Network {

    companion object {
        fun getRetrofit(): Routes {
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(Routes::class.java)

            return service
        }
    }
}