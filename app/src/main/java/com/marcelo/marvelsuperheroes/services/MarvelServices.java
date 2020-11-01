package com.marcelo.marvelsuperheroes.services;

import com.marcelo.marvelsuperheroes.models.HeroDataWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelServices {

    @GET("v1/public/characters")
    Call<HeroDataWrapper> getHeroes(
            @Query("ts") String ts,
            @Query("apikey") String apiKey,
            @Query("hash") String hash,
            @Query("orderBy") String orderBy,
            @Query("limit") int limit,
            @Query("offset") int offset);

}
