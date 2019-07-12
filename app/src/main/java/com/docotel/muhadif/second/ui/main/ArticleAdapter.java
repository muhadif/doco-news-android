package com.docotel.muhadif.second.ui.main;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.docotel.muhadif.second.R;
import com.docotel.muhadif.second.data.model.Article;
import com.docotel.muhadif.second.util.DateUtil;
import com.docotel.muhadif.second.util.GlideUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Article> articles;
    private static final int NORMAL = 1;
    private static final int LARGE = 2;
    private OnItemClickListener onItemClickListener;

    public ArticleAdapter(Context context, List<Article> articles){
        this.articles = articles;
        this.context = context;
    }

    public void setArticles(List<Article> articles){
        this.articles = articles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case NORMAL :
                View viewNormal = layoutInflater.inflate(R.layout.item_article, parent, false);
                viewHolder = new NormalHolder(viewNormal);
                break;
            case LARGE :
                View viewLarge = layoutInflater.inflate(R.layout.item_article_large, parent, false);
                viewHolder = new LargeHolder(viewLarge);
                break;
        }

        return viewHolder;

    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Article article = articles.get(position);

        switch (getItemViewType(position)) {
            case NORMAL :
                final NormalHolder normalHolder = (NormalHolder) holder;
                try {
                    normalHolder.Binding(article, context);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case LARGE :
                final LargeHolder largeHolder = (LargeHolder) holder;
                try {
                    largeHolder.Binding(article, context);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position % 4 == 0 ) {
            return LARGE;
        } else {
            return NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return articles.size() ;
    }



    public class LargeHolder extends RecyclerView.ViewHolder{

        private View itemView;
        private TextView tvTitleItemArticleItemLarge, tvDescriptionItemArticleItemLarge, tvAuthorItemArticleItemLarge, tvDateItemArticleItemLarge;
        private ImageView ivArticleItemLarge;
        private Article article;


        public LargeHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvTitleItemArticleItemLarge = itemView.findViewById(R.id.tv_title_item_article_large);
            ivArticleItemLarge = itemView.findViewById(R.id.iv_item_article_large);
            tvDescriptionItemArticleItemLarge = itemView.findViewById(R.id.tv_description_item_article_large);
            tvAuthorItemArticleItemLarge = itemView.findViewById(R.id.tv_author_item_article_large);
            tvDateItemArticleItemLarge = itemView.findViewById(R.id.tv_date_item_article_large);
        }

        public void Binding(final Article article, final Context context) throws ParseException {
            this.article = article;
            tvTitleItemArticleItemLarge.setText(article.getTitle());
            tvDescriptionItemArticleItemLarge.setText(article.getDescription());

            if(TextUtils.isEmpty(article.getAuthor())){
                tvAuthorItemArticleItemLarge.setText(article.getSource().getName());
            } else {
                tvAuthorItemArticleItemLarge.setText(article.getAuthor());
            }

            tvDateItemArticleItemLarge.setText(article.getDate());




            Glide.with(context)
                    .load(article.getUrlToImage())
                    .apply(GlideUtil.options)
                    .into(ivArticleItemLarge);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(article);
                }
            });
        }

    }

    public class NormalHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView tvTitleItemArticle, tvAuthorItemArticle, tvDateItemArticle;
        private ImageView ivArticleItem;
        private Article article;


        public NormalHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.article = article;
            tvTitleItemArticle = itemView.findViewById(R.id.tv_title_item_article);
            tvAuthorItemArticle = itemView.findViewById(R.id.tv_author_item_article);
            tvDateItemArticle = itemView.findViewById(R.id.tv_date_item_article);
            ivArticleItem = itemView.findViewById(R.id.iv_article_item);

        }


        public void Binding(final Article article, final Context context) throws ParseException {
            this.article = article;

            tvTitleItemArticle.setText(article.getTitle());

            if(TextUtils.isEmpty(article.getAuthor())){
                tvAuthorItemArticle.setText(article.getSource().getName());
            } else {
                tvAuthorItemArticle.setText(article.getAuthor());
            }

            Date publishDate = DateUtil.convertToDate(article.getPublishedAt());

            Date dateNow =  new Date(System.currentTimeMillis());
            Map<TimeUnit,Long> diffDate =  DateUtil.computeDiff(publishDate, dateNow);

            if(diffDate.get(TimeUnit.DAYS) < 1) {
                tvDateItemArticle.setText(diffDate.get(TimeUnit.HOURS).toString() + " hours ago");
            } else {
                tvDateItemArticle.setText("more than 1 day");
            }

            Glide.with(context)
                    .load(article.getUrlToImage())
                    .apply(GlideUtil.options)
                    .into(ivArticleItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(article);
                }
            });
        }


    }


    public interface OnItemClickListener {
        public void onItemClick(Article article);
    }

    public void SetOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
