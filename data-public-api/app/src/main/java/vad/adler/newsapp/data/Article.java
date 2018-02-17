package vad.adler.newsapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.UUID;

/**
 * News article POJO.
 */

public class Article {
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
    private final Date mPublishedAt;

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
                   @Nullable Date publishedAt) {
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
        mPublishedAt = new Date();
    }

}