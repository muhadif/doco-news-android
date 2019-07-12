package com.docotel.muhadif.second.ui.main;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.docotel.muhadif.second.data.model.Article;
import com.docotel.muhadif.second.data.model.NewsRespon;
import com.docotel.muhadif.second.data.repo.RetroClient;
import com.docotel.muhadif.second.util.JNIHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter  {

    MainContract view;
    Context context;

    public MainPresenter(MainContract view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getArticles(Integer page) {
        view.onLoading();
        Call<NewsRespon> responCall = RetroClient.getService().getArticles(page, JNIHelper.getApi());

        responCall.enqueue(new Callback<NewsRespon>() {
            @Override
            public void onResponse(Call<NewsRespon> call, Response<NewsRespon> response) {
                if(response.body() == null) {
                    Toast.makeText(context, "Cannot load more page", Toast.LENGTH_SHORT).show();
                } else {
                    view.loadData(response.body().getArticles());
                    Log.d("ARTICLESPRESETNTER", "Article page = " + response.body().getArticles().size());
                }
                view.finishLoading();
            }

            @Override
            public void onFailure(Call<NewsRespon> call, Throwable t) {
                Toast.makeText(context, "Load Failed", Toast.LENGTH_SHORT).show();
                view.finishLoading();
            }
        });
    }

    public void getFilter(List<Article> articles){

    }
}
