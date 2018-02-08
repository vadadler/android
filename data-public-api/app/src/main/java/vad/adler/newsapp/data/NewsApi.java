package vad.adler.newsapp.data;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interfaces which defines possible HTTP operations.
 */

public interface NewsApi {
    @GET("top-headlines")
    Flowable<List<Article>> getHeadlinesCountry(@Query("country") String country,
                                                @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Flowable<List<Article>> getHeadlinesSources(@Query("sources") String sources,
                                                @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Flowable<List<Article>> getHeadlinesCategory(@Query("country") String country,
                                                 @Query("category") String category,
                                                 @Query("apiKey") String apiKey);
}
