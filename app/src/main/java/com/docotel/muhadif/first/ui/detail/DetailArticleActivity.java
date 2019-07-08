package com.docotel.muhadif.first.ui.detail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.docotel.muhadif.first.R;
import com.docotel.muhadif.first.data.model.Article;

public class DetailArticleActivity extends AppCompatActivity {

    public static final String EXTRA_ARTICLE = "extra_movie";

    private TextView title;
    private ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        title = findViewById(R.id.tv_title_detail);

        setCustomActionBar();
        buttonBack = findViewById(R.id.btn_back);

        if (getIntent().getStringExtra(EXTRA_ARTICLE) != null) {
            String article = getIntent().getStringExtra(EXTRA_ARTICLE);
            title.setText(article);
        }


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
