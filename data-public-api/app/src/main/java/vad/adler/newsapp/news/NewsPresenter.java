package vad.adler.newsapp.news;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import vad.adler.newsapp.data.Article;
import vad.adler.newsapp.data.NewsRepository;


/**
 * Listens to user actions from the UI ({@link vad.adler.newsapp.MainActivity}), retrieves the data and updates the
 * UI as required.
 * <p/>
 * By marking the constructor with {@code @Inject}, Dagger injects the dependencies required to
 * create an instance of the NewsPresenter (if it fails, it emits a compiler error).  It uses
 * {@link NewsModule} to do so.
 * <p/>
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually and bypasses Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 **/

public class NewsPresenter implements NewsContract.Presenter {

    private final NewsRepository mNewsRepository;

    @Nullable
    private NewsContract.View mNewsView;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    NewsPresenter(NewsRepository newsRepository) {
        mNewsRepository = newsRepository;
    }

    @Override
    public void subscribe() {}

    @Override
    public void unsubscribe() {}

    @Override
    public void getNews() {}

    @Override
    public void getArticle(Article article) {}
}
