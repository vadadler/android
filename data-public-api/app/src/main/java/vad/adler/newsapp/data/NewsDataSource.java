package vad.adler.newsapp.data;

import android.database.Observable;
import android.support.annotation.NonNull;
import java.util.List;

/**
 *
 */

public interface NewsDataSource {
    Observable<List<Article>> getNews();
    Observable<Article> getNewsStory(@NonNull String storyId);
}
