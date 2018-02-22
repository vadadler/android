package vad.adler.newsapp.data;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Implementation of model interface.
 */

public class NewsRepository implements NewsDataSource {
    @Inject
    public NewsRepository() {};

    /**
     * Current implementation fetches latest news from Newsapi (REST), then stores them in SQLite
     * database and then returns results from that SQLite db.
     * @return
     */
    @Override
    public Flowable<List<Article>> getNews() {
        Flowable<List<Article>> articles = null;
        return articles;
    }

    @Override
    public Flowable<Article> getArticle(@NonNull String articleId) {
        Article article = new Article();
        return Flowable.just(article);
    }
}
