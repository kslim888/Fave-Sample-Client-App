package com.kslimweb.favetechnicalassignment

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("ReadFirestore")
    fun getMerchantsDetails(@Query("merchant") merchant: String): Call<List<MerchantDetail>>

//    fun getMerchantsDetails(@Query("merchant") String merchant): Call<List<MerchantDetail>>
}