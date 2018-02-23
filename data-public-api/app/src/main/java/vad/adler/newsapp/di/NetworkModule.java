package vad.adler.newsapp.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import vad.adler.newsapp.Constants;

/**
 * DI: provide objects which can be injected.
 */
@Module
public class NetworkModule {
    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
