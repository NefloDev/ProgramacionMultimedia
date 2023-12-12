package com.example.examenfinal.pokeapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.example.examenfinal.models.*;

public interface PokeAPIService {
    String BASE_URL = "https://pokeapi.co/api/v2/";

    @GET("move/{name}")
    Call<Move> getMoveById(@Path("name") String id);

    @GET("move")
    Call<MoveList> getMoveList(@Query("limit") int limit, @Query("offset") int offset);

    @GET("item/{name}")
    Call<Item> getItemById(@Path("name") String name);

    @GET("item")
    Call<ItemList> getItemList(@Query("limit") int limit, @Query("offset") int offset);
}
