package vad.adler.newsapp.data;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;

/**
 * Model interface.
 */

public interface NewsDataSource {
    Observable<List<Article>> getNews();
    Observable<Article> getArticle(@NonNull String articleId);
}
