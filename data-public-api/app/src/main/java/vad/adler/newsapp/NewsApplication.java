package vad.adler.newsapp;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;
import com.example.android.architecture.blueprints.todoapp.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import vad.adler.newsapp.data.NewsRepository;

/**
 * We create a custom {@link Application} class that extends  {@link DaggerApplication}.
 * We then override applicationInjector() which tells Dagger how to make our @Singleton Component
 * We never have to call `component.inject(this)` as {@link DaggerApplication} will do that for us.
 */
public class NewsApplication extends DaggerApplication {
    @Inject
    NewsRepository newsRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    /**
     * Espresso tests need to be able to get an instance of the {@link NewsRepository}
     * so that we can delete all tasks before running each test
     */
    @VisibleForTesting
    public NewsRepository getTasksRepository() {
        return newsRepository;
    }
}
