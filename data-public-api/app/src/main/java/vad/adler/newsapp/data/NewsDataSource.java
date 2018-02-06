package vad.adler.newsapp.data;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Model interface.
 */

public interface NewsDataSource {
    Flowable<List<Article>> getNews();
    Flowable<Article> getArticle(@NonNull String articleId);
}
