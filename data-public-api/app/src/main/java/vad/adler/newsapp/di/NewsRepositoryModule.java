package vad.adler.newsapp.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import vad.adler.newsapp.data.NewsDao;
import vad.adler.newsapp.data.NewsDataSource;
import vad.adler.newsapp.data.NewsDatabase;

/**
 * This is used by Dagger to inject the required arguments into the {@link NewsRepository}.
 */
@Module
abstract public class NewsRepositoryModule {

    private static final int THREAD_COUNT = 3;

    @Singleton
    @Binds
    abstract NewsDataSource provideTasksLocalDataSource(NewsDataSource dataSource);

    @Singleton
    @Provides
    static NewsDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), NewsDatabase.class, "news.db")
                .build();
    }

    @Singleton
    @Provides
    static NewsDao provideTasksDao(NewsDatabase db) {
        return db.newsDao();
    }

    @Singleton
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }
}
