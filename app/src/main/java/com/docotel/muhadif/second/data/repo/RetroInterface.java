package com.docotel.muhadif.second.data.repo;

import com.docotel.muhadif.second.data.model.NewsRespon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroInterface {

    @GET("top-headlines?country=us&pageSize=10" )
    public Call<NewsRespon> getArticles(@Query("page") int page, @Query("apiKey") String apiKey);

}
