package project3.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an article from the NewsAPI format.
 * Implements the {@link Article} interface and maps JSON fields to class properties.
 */
public class NewsAPIArticle implements Article {
    private final String title;
    private final String description;
    private final String publishedAt;
    private final String url;
    private final String urlToImage;
    private final String author;
    private final String content;
    private final Source source;

    /**
     * Constructor to initialize a NewsAPIArticle instance with JSON properties.
     *
     * @param title The title of the article.
     * @param description The description of the article.
     * @param publishedAt The published date of the article.
     * @param url The URL of the article.
     * @param urlToImage The URL to an image associated with the article.
     * @param author The author of the article.
     * @param content The content of the article.
     * @param source The source information of the article.
     */
    public NewsAPIArticle(
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("publishedAt") String publishedAt,
            @JsonProperty("url") String url,
            @JsonProperty("urlToImage") String urlToImage,
            @JsonProperty("author") String author,
            @JsonProperty("content") String content,
            @JsonProperty("source") Source source) {
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.url = url;
        this.urlToImage = urlToImage;
        this.author = author;
        this.content = content;
        this.source = source;
    }

    /**
     * Inner class representing the source of a NewsAPI article.
     */
    public static class Source {
        private final String id;
        private final String name;

        /**
         * Constructor to initialize a Source instance with the source information.
         *
         * @param id The unique identifier for the source.
         * @param name The name of the source.
         */
        public Source(@JsonProperty("id") String id, @JsonProperty("name") String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * Gets the source ID.
         *
         * @return The source ID.
         */
        public String getId() { return id; }

        /**
         * Gets the source name.
         *
         * @return The source name.
         */
        public String getName() { return name; }

        /**
         * Returns a string representation of the source.
         *
         * @return The source details in string format.
         */
        @Override
        public String toString() {
            return "Source: " + name + " (ID: " + id + ")";
        }
    }

    @Override
    public String getTitle() { return title; }

    @Override
    public String getDescription() { return description; }

    @Override
    public String getPublishedAt() { return publishedAt; }

    @Override
    public String getUrl() { return url; }

    /**
     * Gets the source information of the article.
     *
     * @return The source of the article.
     */
    public Source getSource() { return source; }

    @Override
    public String toString() {
        return toFormattedString();
    }
}
