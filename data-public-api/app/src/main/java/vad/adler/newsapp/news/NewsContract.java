package vad.adler.newsapp.news;

import vad.adler.newsapp.BasePresenter;
import vad.adler.newsapp.BaseView;

/**
 *
 */
public interface NewsContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
        /**
         * Get latest news from newsapi.org
         */
        void getNews();

    }
}
