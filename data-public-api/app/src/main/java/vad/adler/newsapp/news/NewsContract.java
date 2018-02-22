package vad.adler.newsapp.news;

import android.support.annotation.NonNull;

import java.util.List;

import vad.adler.newsapp.BasePresenter;
import vad.adler.newsapp.BaseView;
import vad.adler.newsapp.data.Article;

/**
 *
 */
public interface NewsContract {

    interface View extends BaseView<Presenter> {
        /**
         * Show list of {@link Article articles}.
         * @param articles
         */
        void showNews(List<Article> articles);

        void showLoadingNewsError();
    }

    interface Presenter extends BasePresenter {
        /**
         * Get latest news from newsapi.org
         */
        void getNews();

        /**
         * Get a specific news article.
         * @param article
         */
        void getArticle(@NonNull Article article);
    }
}
