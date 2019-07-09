package com.docotel.muhadif.first.data.service;

import android.os.Build;

import com.docotel.muhadif.first.BuildConfig;
import com.docotel.muhadif.first.data.model.NewsRespon;
import com.docotel.muhadif.first.data.model.ResultNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroInterface {
    public static final String API_KEY = BuildConfig.API_KEY;

    @GET("top-headlines?country=us&apiKey=" + API_KEY + "&pageSize=10" )
    public Call<NewsRespon> getArticles(@Query("page") int page);




}
