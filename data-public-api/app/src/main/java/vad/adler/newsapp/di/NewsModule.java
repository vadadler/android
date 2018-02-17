package vad.adler.newsapp.di;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import vad.adler.newsapp.MainActivity;
import vad.adler.newsapp.news.NewsContract;
import vad.adler.newsapp.news.NewsPresenter;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link vad.adler.newsapp.news.NewsPresenter}.
 */
@Module
public abstract class NewsModule {
    @ActivityScoped
    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ActivityScoped
    @Binds
    abstract NewsContract.Presenter taskPresenter(NewsPresenter presenter);
}
