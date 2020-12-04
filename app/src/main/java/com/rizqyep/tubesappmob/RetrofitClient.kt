package com.rizqyep.tubesappmob

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://appmob-api.et.r.appspot.com/";

    val instance : Api by lazy {
       val retrofit = Retrofit.Builder()
           .baseUrl(BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build()

        retrofit.create(Api::class.java)
    }

}