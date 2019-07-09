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
import com.docotel.muhadif.first.util.DateUtil;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailArticleActivity extends AppCompatActivity {

    public static final String EXTRA_ARTICLE = "extra_movie";

    private TextView title, description, author, date, customActioBar;
    private ImageButton buttonBack;
    private Article article;
    private ImageView picture;

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
        setCustomActionBar();

        customActioBar = findViewById(R.id.tv_detail_action_bar);
        title = findViewById(R.id.tv_title_detail);
        author = findViewById(R.id.tv_author_detail);
        date = findViewById(R.id.tv_date_detail);
        description = findViewById(R.id.tv_detail_description);
        picture = findViewById(R.id.iv_detail_article);
        buttonBack = findViewById(R.id.btn_back);
    }

    private void setContent() throws ParseException {
        if (getIntent().getStringExtra(EXTRA_ARTICLE) != null) {
            try {
                String articleJson = getIntent().getStringExtra(EXTRA_ARTICLE);
                Gson gson = new Gson();
                article = gson.fromJson(articleJson, Article.class);

            } catch (Exception e){
                Log.e("PARSINGJSON", e.getMessage());
            }
        }

        Date publishDateNonFormatted = DateUtil.convertToDate(article.getPublishedAt());
        DateFormat dateFormat = new SimpleDateFormat("d MMMM");
        String publishDate = dateFormat.format(publishDateNonFormatted);

        customActioBar.setText(article.getSource().getName());
        title.setText(article.getTitle());
        description.setText(article.getContent());
        author.setText(article.getAuthor());
        date.setText(publishDate);


        Glide.with(this)
                .load(article.getUrlToImage())
                .into(picture);

    }

    private void initListener(){
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
