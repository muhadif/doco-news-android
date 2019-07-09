package com.docotel.muhadif.first.ui.detail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.docotel.muhadif.first.R;
import com.docotel.muhadif.first.data.model.Article;
import com.google.gson.Gson;

public class DetailArticleActivity extends AppCompatActivity {

    public static final String EXTRA_ARTICLE = "extra_movie";

    private TextView title, description;
    private ImageButton buttonBack;
    private Article article;
    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        title = findViewById(R.id.tv_title_detail);
        description = findViewById(R.id.tv_detail_description);
        picture = findViewById(R.id.iv_detail_article);

        setCustomActionBar();
        buttonBack = findViewById(R.id.btn_back);

        if (getIntent().getStringExtra(EXTRA_ARTICLE) != null) {

            try {
                String articleJson = getIntent().getStringExtra(EXTRA_ARTICLE);
                Gson gson = new Gson();
                article = gson.fromJson(articleJson, Article.class);

            } catch (Exception e){
                Log.e("PARSINGJSON", e.getMessage());
            }
        }

        title.setText(article.getTitle());
        description.setText(article.getContent());

        Glide.with(this)
                .load(article.getUrlToImage())
                .into(picture);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setCustomActionBar() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.doco_detail_action_bar);
        getSupportActionBar().setElevation(0);

    }
}
