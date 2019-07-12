package com.docotel.muhadif.second.ui.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.docotel.muhadif.second.R;
import com.docotel.muhadif.second.data.model.Article;
import com.docotel.muhadif.second.util.DateUtil;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailArticleActivity extends AppCompatActivity {

    public static final String EXTRA_ARTICLE = "extra_movie";

    private TextView tvTitleDetail, tvDetailActionBar, tvAuthorDetail, tvDateDetail, tvDescriptionDetail;
    private ImageButton ibBackDetail, ibShareBottomDetail;
    public Article article;
    private ImageView ivArticleDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        init();

        try {
            setContent();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        initListener();

    }

    private void init(){

        tvDetailActionBar = findViewById(R.id.tv_detail_action_bar);
        tvTitleDetail = findViewById(R.id.tv_title_detail);
        tvAuthorDetail = findViewById(R.id.tv_author_detail);
        tvDateDetail = findViewById(R.id.tv_date_detail);
        tvDescriptionDetail = findViewById(R.id.tv_description_detail);
        ivArticleDetail = findViewById(R.id.iv_article_detail);
        ibBackDetail = findViewById(R.id.ib_back_detail);
        ibShareBottomDetail = findViewById(R.id.ib_share_bottom_detail);
    }

    private void shareArticle(Article article) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, article.getTitle());
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Judul Berita : " + article.getTitle() + "\n" + article.getDescription() + " (sumber : " + article.getUrl() + ")");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void setContent() throws ParseException {
        if (getIntent().getStringExtra(EXTRA_ARTICLE) != null) {
            try {
                String articleJson = getIntent().getStringExtra(EXTRA_ARTICLE);
                Gson gson = new Gson();
                this.article = gson.fromJson(articleJson, Article.class);

            } catch (Exception e){
                Log.e("PARSINGJSON", e.getMessage());
            }
        }

        Date publishDateNonFormatted = DateUtil.convertToDate(article.getPublishedAt());
        DateFormat dateFormat = new SimpleDateFormat("d MMMM");
        String publishDate = dateFormat.format(publishDateNonFormatted);

        tvDetailActionBar.setText(article.getSource().getName());
        tvTitleDetail.setText(article.getTitle());
        tvDescriptionDetail.setText(article.getContent());
        tvAuthorDetail.setText(article.getAuthor());
        tvDateDetail.setText(publishDate);


        Glide.with(this)
                .load(article.getUrlToImage())
                .into(ivArticleDetail);

    }

    private void initListener(){
        ibBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ibShareBottomDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareArticle(article);
            }
        });
    }

}
