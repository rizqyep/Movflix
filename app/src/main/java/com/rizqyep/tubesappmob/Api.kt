package com.rizqyep.tubesappmob

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("movies")
    fun getMovies(): Call<ArrayList<MovieResponse>>

    @FormUrlEncoded
    @POST("movies")
    fun createMovie(
        @Field("name") name : String,
        @Field("year_released") year_released : String,
        @Field("rating") rating : Double
    ) : Call<CreateMovieResponse>
}
