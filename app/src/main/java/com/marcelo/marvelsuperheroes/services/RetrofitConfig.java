package com.marcelo.marvelsuperheroes.services;

import com.marcelo.marvelsuperheroes.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(Constants.MARVEL_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public MarvelServices getMarvelService() {
        return this.retrofit.create(MarvelServices.class);
    }

}