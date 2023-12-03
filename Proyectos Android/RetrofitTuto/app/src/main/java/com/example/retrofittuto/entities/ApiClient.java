package com.example.retrofittuto.entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiClient {
    @GET("pokemon/")
    Call<ApiResponse> getPokemons(@Query("limit") int limit, @Query("offset") int offset);
}
