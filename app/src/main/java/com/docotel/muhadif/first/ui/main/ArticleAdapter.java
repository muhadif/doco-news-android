package com.docotel.muhadif.first.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.docotel.muhadif.first.R;
import com.docotel.muhadif.first.data.model.Article;
import com.docotel.muhadif.first.ui.detail.DetailArticleActivity;
import com.google.gson.Gson;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private Context context;
    private List<Article> articles;

    public ArticleAdapter(Context context, List<Article> articles){
        this.context = context;
        this.articles = articles;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_article, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Binding(articles.get(position), context);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View itemView;
        private TextView title, source, date;
        private ImageView picture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            title = itemView.findViewById(R.id.tv_article_title);
            source = itemView.findViewById(R.id.tv_article_source);
            date = itemView.findViewById(R.id.tv_article_date);
            picture = itemView.findViewById(R.id.iv_article);

        }

        public void Binding(final Article article, final Context context){
            title.setText(article.getTitle());
            source.setText(article.getSource().getName());

            String formatedDate[] = article.getPublishedAt().split("T");

            date.setText(formatedDate[0]);

            Glide.with(context)
                    .load(article.getUrlToImage())
                    .into(picture);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailArticle = new Intent(context, DetailArticleActivity.class);

                    Gson gson = new Gson();
                    String acticleJson = gson.toJson(article);

                    detailArticle.putExtra(DetailArticleActivity.EXTRA_ARTICLE, acticleJson);
                    detailArticle.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(detailArticle);
                }
            });

        }
    }
}
