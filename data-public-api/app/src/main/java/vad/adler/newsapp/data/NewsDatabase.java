package vad.adler.newsapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * The Room Database that contains the Task table.
 */
@Database(entities = {Article.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();
}
