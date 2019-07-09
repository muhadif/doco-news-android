package com.docotel.muhadif.first.ui.main;

import com.docotel.muhadif.first.data.model.Article;

import java.util.List;

public interface MainContract {

        void onLoading();

        void finishLoading();

        void loadData(List<Article> articles);

}
