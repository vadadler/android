package vad.adler.newsapp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Data Access Object for the news table.
 */
@Dao
public interface NewsDao {

    /**
     * Select all articles from the news table.
     *
     * @return all articles.
     */
    @Query("SELECT * FROM news")
    List<Article> getTasks();

    /**
     * Select a article by id.
     *
     * @param articleId the task id.
     * @return the articles with taskId.
     */
//    @Query("SELECT * FROM news WHERE articleId = :articleId")
//    Article getArticleById(String articleId);

    /**
     * Insert an article in the database. If such article already exists, replace it.
     *
     * @param article the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(Article article);
}
