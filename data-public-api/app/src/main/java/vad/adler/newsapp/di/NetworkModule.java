package vad.adler.newsapp.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

//    @Provides
//    @Singleton
//    NewsApi provideNewsApi(Retrofit retrofit) {
//        return retrofit.create(NewsApi.class);
//    }

//    @GET("top-headlines")
//    Flowable<List<Article>> getHeadlinesCountry(@Query("country") String country,
//                                                @Query("apiKey") String apiKey);
//
//    @GET("top-headlines")
//    Flowable<List<Article>> getHeadlinesSources(@Query("sources") String sources,
//                                                @Query("apiKey") String apiKey);
//
//    @GET("top-headlines")
//    Flowable<List<Article>> getHeadlinesCategory(@Query("country") String country,
//                                                 @Query("category") String category,
//                                                 @Query("apiKey") String apiKey);

}
