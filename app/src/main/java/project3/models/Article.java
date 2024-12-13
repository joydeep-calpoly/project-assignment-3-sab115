package project3.models;

/**
 * Interface representing a News Article.
 * Provides common properties for articles like title, description, published date, and URL.
 */
public interface Article {

    /**
     * Gets the title of the article.
     *
     * @return The title of the article.
     */
    String getTitle();

    /**
     * Gets the description of the article.
     *
     * @return The description of the article.
     */
    String getDescription();

    /**
     * Gets the published date of the article.
     *
     * @return The published date of the article.
     */
    String getPublishedAt();

    /**
     * Gets the URL of the article.
     *
     * @return The URL of the article.
     */
    String getUrl();

    /**
     * Validates the article to ensure it contains essential data.
     *
     * @return true if the article has a title, description, published date, and URL; false otherwise.
     */
    default boolean isValid() {
        return getTitle() != null && !getTitle().isEmpty() &&
                getDescription() != null && !getDescription().isEmpty() &&
                getPublishedAt() != null && !getPublishedAt().isEmpty() &&
                getUrl() != null && !getUrl().isEmpty();
    }

    /**
     * Returns a formatted string representation of the article.
     *
     * @return A string with the article's title, description, published date, and URL.
     */
    default String toFormattedString() {
        return "Title: " + getTitle() + "\n" +
                "Description: " + getDescription() + "\n" +
                "Published At: " + getPublishedAt() + "\n" +
                "URL: " + getUrl();
    }

}