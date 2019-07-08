package com.docotel.muhadif.first.ui.main;

import com.docotel.muhadif.first.data.model.Article;

import java.util.List;

public interface MainContract {

    interface View {
        void setLoading(Boolean isLoading);

        void loadData(List<Article> articles);
    }

    interface Presenter{
        void getArticles(Integer page);
    }

}
