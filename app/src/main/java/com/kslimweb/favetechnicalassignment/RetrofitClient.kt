package com.kslimweb.favetechnicalassignment

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://web-api-dot-fave-technical-assignment.appspot.com/"
    private var retrofit: Retrofit? = null

    val retrofitInstance: Retrofit?
    get() {
        if(retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

}