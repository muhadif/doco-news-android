package com.docotel.muhadif.second.data.repo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.docotel.muhadif.second.util.DataConfig.BASE_URL;

public class RetroClient {

    public static Retrofit.Builder builder() {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
    }

    public static Retrofit getRetrofit() { return builder().build(); }

    public static RetroInterface getService() { return getRetrofit().create(RetroInterface.class);}

}
