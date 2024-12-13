package project3.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a simple article structure used for parsing a simplified JSON format.
 * Implements the {@link Article} interface.
 */
public class SimpleArticle implements Article {
    private final String title;
    private final String description;
    private final String publishedAt;
    private final String url;

    /**
     * Constructor to initialize a SimpleArticle instance with JSON properties.
     *
     * @param title The title of the article.
     * @param description The description of the article.
     * @param publishedAt The published date of the article.
     * @param url The URL of the article.
     */
    public SimpleArticle(
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("publishedAt") String publishedAt,
            @JsonProperty("url") String url) {
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.url = url;
    }

    @Override
    public String getTitle() { return title; }

    @Override
    public String getDescription() { return description; }

    @Override
    public String getPublishedAt() { return publishedAt; }

    @Override
    public String getUrl() { return url; }

    @Override
    public String toString() {
        return toFormattedString();
    }

}
