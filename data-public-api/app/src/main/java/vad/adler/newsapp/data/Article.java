package vad.adler.newsapp.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * News article POJO.
 */
@Entity(tableName = "news")
public class Article {
    @PrimaryKey
    @SerializedName("id")
    @NonNull
    private final String mId;

    /**
     * Display name for the source the article came from.
     */
    @SerializedName("name")
    @NonNull
    private final String mSource;

    /**
     * The author of the article.
     */
    @SerializedName("author")
    @NonNull
    private final String mAuthor;

    /**
     * The headline or title of the article.
     */
    @SerializedName("title")
    @NonNull
    private final String mTitle;

    /**
     * A description or snippet from the article.
     */
    @SerializedName("description")
    @NonNull
    private final String mDescriotion;

    /**
     * The direct URL to the article.
     */
    @SerializedName("url")
    @NonNull
    private final String mUrl;

    /**
     * The URL to a relevant image for the article.
     */
    @SerializedName("urlToImage")
    @NonNull
    private final String mUrlToImage;

    /**
     * The date and time that the article was published, in UTC (+000).
     */
    @SerializedName("publishedAt")
    @NonNull
    private final String mPublishedAt;

    /**
     * Constructor for individual article.
     *
     * @param source
     * @param author
     * @param title
     * @param description
     * @param url
     * @param urlToImage
     * @param publishedAt
     */
    public Article(@Nullable String source, @Nullable String author, @Nullable String title,
                   @Nullable String description, @Nullable String url, @Nullable String urlToImage,
                   @Nullable String publishedAt) {
        mId = UUID.randomUUID().toString();
        mSource = source;
        mAuthor = author;
        mTitle = title;
        mDescriotion = description;
        mUrl = url;
        mUrlToImage = urlToImage;
        mPublishedAt = publishedAt;
    }

    /**
     * Default constructor.
     * TODO: Do we need this? This is a temp implementation to allow prototyping.
     */
    public Article() {
        mId = UUID.randomUUID().toString();
        mSource = "";
        mAuthor = "";
        mTitle = "";
        mDescriotion = "";
        mUrl = "";
        mUrlToImage = "";
        mPublishedAt = "";
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public String getSource() {
        return mSource;
    }

    @NonNull
    public String getAuthor() {
        return mAuthor;
    }

    @NonNull
    public String getTitle() { return  mTitle; }

    @NonNull
    public String getDescriotion() { return  mDescriotion; }

    @NonNull
    public String getUrl() { return  mUrl; }

    @NonNull
    String getUrlToImage() { return  mUrlToImage; }

    @NonNull
    String getPublishedAt() { return  mPublishedAt; }
}
