package com.docotel.muhadif.second.ui.main;

import com.docotel.muhadif.second.data.model.Article;

import java.util.List;

public interface MainContract {

        void onLoading();

        void finishLoading();

        void loadData(List<Article> articles);

}
