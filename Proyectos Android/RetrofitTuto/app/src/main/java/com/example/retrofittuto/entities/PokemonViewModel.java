package com.example.retrofittuto.entities;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonViewModel extends AndroidViewModel {
    public MutableLiveData<ApiResponse> responseMutableLiveData = new MutableLiveData<>();
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static ApiClient api = retrofit.create(ApiClient.class);

    public PokemonViewModel(@NonNull Application application) {
        super(application);
    }

    public void search(String offset){
        Call<ApiResponse> response = api.getPokemons(20, Integer.parseInt(offset));
        response.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful()){
                    responseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("Error", "Error");
            }
        });
    }
}
