package com.docotel.muhadif.second.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.docotel.muhadif.second.R;
import com.docotel.muhadif.second.data.model.Article;
import com.docotel.muhadif.second.ui.detail.DetailArticleActivity;
import com.docotel.muhadif.second.util.EndlessRecyclerViewScrollListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract {

    private MainPresenter presenter;
    private List<Article> articles = new ArrayList<>();
    private List<Article> filteredArticles = new ArrayList<>();
    private RecyclerView rvArticle;
    private ArticleAdapter articleAdapter;
    private SwipeRefreshLayout swipeArticleList;
    public static int page = 1;
    private boolean loading = false;
    private LinearLayoutManager linearLayoutManager;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private View constSearch;
    private ImageButton ibSearch, ibClearSearch, ibCloseSearch;
    private EditText etSearch;
    private TextView tvNoExitingNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this, getApplicationContext());

        init();

        getData(page);

        initListener();


    }

    private void init() {
        swipeArticleList = findViewById(R.id.swipe_article_list);
        articleAdapter = new ArticleAdapter(this, articles);
        rvArticle = findViewById(R.id.rv_article);
        linearLayoutManager = new LinearLayoutManager(this);
        rvArticle.setLayoutManager(linearLayoutManager);
        constSearch = findViewById(R.id.const_search);
        ibClearSearch = findViewById(R.id.ib_clear_search);
        ibCloseSearch = findViewById(R.id.ib_close_search);
        ibSearch = findViewById(R.id.ib_search);
        etSearch = findViewById(R.id.et_search);
        tvNoExitingNews = findViewById(R.id.tv_no_exiting_news);

        rvArticle.setAdapter(articleAdapter);

    }

    private void initListener() {
        swipeArticleList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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

        articleAdapter.SetOnItemClickListener(new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Article article) {
                Intent detailArticle = new Intent(MainActivity.this, DetailArticleActivity.class);

                Gson gson = new Gson();
                String acticleJson = gson.toJson(article);

                detailArticle.putExtra(DetailArticleActivity.EXTRA_ARTICLE, acticleJson);
                detailArticle.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(detailArticle);
            }
        });

        rvArticle.addOnScrollListener(endlessRecyclerViewScrollListener);

        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constSearch.setVisibility(View.VISIBLE);
                rvArticle.removeOnScrollListener(endlessRecyclerViewScrollListener);
            }
        });

        ibClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etSearch.getText().toString().isEmpty()) {
                    articleAdapter.setArticles(articles);
                } else {
                    etSearch.setText("");
                }

            }
        });

        ibCloseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvArticle.setVisibility(View.VISIBLE);
                tvNoExitingNews.setVisibility(View.GONE);
                constSearch.setVisibility(View.GONE);
                articleAdapter.setArticles(articles);
                rvArticle.addOnScrollListener(endlessRecyclerViewScrollListener);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(etSearch.getText().toString().isEmpty()) {
                    articleAdapter.setArticles(articles);
                    ibClearSearch.setVisibility(View.INVISIBLE);
                } else {
                    ibClearSearch.setVisibility(View.VISIBLE);
                    performFiltering(etSearch.getText().toString());
                }
            }
        });
    }

    @Override
    public void onLoading() {
        swipeArticleList.setRefreshing(true);
        this.loading = true;
    }

    @Override
    public void finishLoading() {
        swipeArticleList.setRefreshing(false);
        this.loading = false;
    }

    @Override
    public void loadData(List<Article> articles) {
        if (page == 1) {
            this.articles.clear();
        }
        this.articles.addAll(articles);
        Log.d("ARTICLES", "Article Count = " + this.articles.size());
        articleAdapter.notifyDataSetChanged();

    }

    private void getData(int page) {
        presenter.getArticles(page);
    }


    public void performFiltering(String searchText) {

        List<Article> filteredList = new ArrayList<>();
        for (Article article : articles) {
            if (article.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(article);
            }
        }

        if (filteredList.size() > 0) {
            rvArticle.setVisibility(View.VISIBLE);
            tvNoExitingNews.setVisibility(View.GONE);
            filteredArticles.clear();
            filteredArticles.addAll(filteredList);
            articleAdapter.setArticles(filteredArticles);
        } else {
            rvArticle.setVisibility(View.INVISIBLE);
            tvNoExitingNews.setVisibility(View.VISIBLE);
        }
    }


}
