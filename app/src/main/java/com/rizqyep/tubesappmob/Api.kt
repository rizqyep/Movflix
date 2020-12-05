package com.rizqyep.tubesappmob

import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET("movies")
    fun getMovies(): Call<ArrayList<MovieResponse>>

    @GET("movies/{id}")
    fun getMovie( @Path("id") id : String): Call<MovieResponse>


    @FormUrlEncoded
    @POST("movies")
    fun createMovie(
        @Field("name") name : String,
        @Field("year_released") year_released : String,
        @Field("rating") rating : Double
    ) : Call<CreateMovieResponse>

    @DELETE("movies/{id}")
    fun deleteMovie(
        @Path("id") id :String
    ) : Call<Void>

    @FormUrlEncoded
    @PUT("movies/{id}")
    fun updateMovie(
        @Path("id") id : String,
        @Field("name") name : String,
        @Field("year_released") year_released : String,
        @Field("rating") rating : Double
    ) : Call<MovieResponse>
}
