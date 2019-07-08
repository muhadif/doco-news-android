package com.docotel.muhadif.first.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.docotel.muhadif.first.R;
import com.docotel.muhadif.first.data.model.Article;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    private MainPresenter presenter;
    private List<Article> articles = new ArrayList<>();
    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page;
    private Boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCustomActionBar();

        init();
    }

    private void setCustomActionBar() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.doco_action_bar);
        getSupportActionBar().setElevation(0);

    }

    private void init(){

        page = 0;

        swipeRefreshLayout = findViewById(R.id.srl_article_list);
        articleAdapter = new ArticleAdapter(this.getApplicationContext(), articles);
        recyclerView = findViewById(R.id.rv_articles);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(articleAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)){

                    if(!getLoading()) {
                        Toast.makeText(MainActivity.this, "Load More", Toast.LENGTH_SHORT).show();
                        page++;
                        presenter.getArticles(page);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                presenter.getArticles(page);
            }
        });

        attachPresenter();

        presenter.getArticles(0);

    }

    private void attachPresenter(){
        presenter = new MainPresenter(this, getApplicationContext());
    }


    @Override
    public void setLoading(Boolean isLoading) {
        swipeRefreshLayout.setRefreshing(isLoading);
        this.isLoading = isLoading;
    }

    @Override
    public void loadData(List<Article> articles) {
        if(page == 0) {
            this.articles.clear();
        }

        this.articles.addAll(articles);
        articleAdapter.notifyDataSetChanged();
        Log.d("Article", "Size news = " + this.articles.size());
    }

    private Boolean getLoading() {
        if (this.isLoading) {
            Toast.makeText(MainActivity.this, "Please wait", Toast.LENGTH_SHORT).show();

        }
        return this.isLoading;
    }
}
