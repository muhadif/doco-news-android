package com.docotel.muhadif.first.ui.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.docotel.muhadif.first.R;
import com.docotel.muhadif.first.data.model.Article;
import com.docotel.muhadif.first.util.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract{

    private MainPresenter presenter;
    private List<Article> articles = new ArrayList<>();
    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static int page = 1;
    private boolean loading = false;
    private LinearLayoutManager linearLayoutManager;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCustomActionBar();

        presenter = new MainPresenter(this, getApplicationContext());

        init();

        getData(page);

        initListener();


    }

    private void setCustomActionBar() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.doco_action_bar);
        getSupportActionBar().setElevation(0);

    }

    private void init(){
        swipeRefreshLayout = findViewById(R.id.srl_article_list);
        articleAdapter = new ArticleAdapter(this, articles);
        recyclerView = findViewById(R.id.rv_articles);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(articleAdapter);

    }

    private void initListener(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                presenter.getArticles(page);
                endlessRecyclerViewScrollListener.resetState();
            }
        });

        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d("ARTICLES", "Article page = " + page);
                MainActivity.page++;
                getData(page);
            }
        };

        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
    }

    @Override
    public void onLoading() {
        swipeRefreshLayout.setRefreshing(true);
        this.loading = true;
    }

    @Override
    public void finishLoading() {
        swipeRefreshLayout.setRefreshing(false);
        this.loading = false;
    }

    @Override
    public void loadData(List<Article> articles) {
        if(page == 1) {
            this.articles.clear();
        }
        this.articles.addAll(articles);
        Log.d("ARTICLES", "Article Count = " + this.articles.size());
        articleAdapter.notifyDataSetChanged();


    }

    private void getData(int page){
        presenter.getArticles(page);
    }
}
