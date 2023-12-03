package com.example.retrofittutokotlin.entities

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonViewModel(application: Application) : AndroidViewModel(application) {

    val responseMutableLiveData  = MutableLiveData<ApiResponse>()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(ApiClient::class.java)

    fun search(offset : String){
        val response = api.getPokemons(20, offset.toInt())

        response.enqueue(object : Callback<ApiResponse>{
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if(response.isSuccessful){
                    responseMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("POKEMON", "Api response error")
            }

        })
    }

}