package vad.adler.newsapp.news;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import vad.adler.newsapp.data.Article;
import vad.adler.newsapp.data.NewsRepository;


/**
 * Listens to user actions from the UI ({@link vad.adler.newsapp.MainActivity}), retrieves the data and updates the
 * UI as required.
 * <p/>
 * By marking the constructor with {@code @Inject}, Dagger injects the dependencies required to
 * create an instance of the NewsPresenter (if it fails, it emits a compiler error).  It uses
 * {@link vad.adler.newsapp.di.NewsModule} to do so.
 * <p/>
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually and bypasses Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 **/

public class NewsPresenter implements NewsContract.Presenter {
    @Inject
    NewsRepository mNewsRepository;

    @Nullable
    private NewsContract.View mNewsView;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    NewsPresenter(NewsRepository newsRepository) {
        mNewsRepository = newsRepository;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() { getNews(); }

    @Override
    public void unsubscribe() { mCompositeDisposable.clear(); }

    @Override
    public void getNews() {
        Disposable disposable = mNewsRepository.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        articles -> {
                            processArticles(articles);
                        },
                        // onError
                        throwable -> mNewsView.showLoadingNewsError());
    }

    @Override
    public void getArticle(Article article) {}

    private void processArticles(@NonNull List<Article> articles) {
        mNewsView.showNews(articles);
    }

}
