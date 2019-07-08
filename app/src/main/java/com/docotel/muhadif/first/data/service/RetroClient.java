package com.docotel.muhadif.first.data.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static final String BASE_URL = "https://newsapi.org/v2/";

    public static Retrofit.Builder builder() {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
    }

    public static Retrofit getRetrofit() { return builder().build(); }

    public static RetroInterface getService() { return getRetrofit().create(RetroInterface.class);}

}
