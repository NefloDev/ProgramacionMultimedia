package com.example.retrofittuto.entities;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiClient {
    @GET("pokemon/")
    Call<ApiResponse> getCards();
}
