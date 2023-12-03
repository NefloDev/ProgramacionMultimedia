package com.example.retrofittutokotlin.entities

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface ApiClient {
    @GET("pokemon/")
    fun getPokemons(@Query("limit") limit : Int, @Query("offset") offset: Int) : Call<ApiResponse>
}