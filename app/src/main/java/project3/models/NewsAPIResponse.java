package project3.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents a response from the NewsAPI.
 * Contains metadata about the response and a list of articles.
 */
public class NewsAPIResponse {
    private final String status;
    private final Integer totalResults;
    private final List<NewsAPIArticle> articles;

    /**
     * Constructor to initialize a NewsAPIResponse instance with JSON properties.
     *
     * @param status The status of the API response.
     * @param totalResults The total number of results returned.
     * @param articles The list of articles in the response.
     */
    public NewsAPIResponse(
            @JsonProperty("status") String status,
            @JsonProperty("totalResults") Integer totalResults,
            @JsonProperty("articles") List<NewsAPIArticle> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    /**
     * Gets the status of the response.
     *
     * @return The status of the response.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets the total number of results returned in the response.
     *
     * @return The total number of results.
     */
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     * Gets the list of articles returned in the response.
     *
     * @return The list of articles.
     */
    public List<NewsAPIArticle> getArticles() {
        return articles;
    }


    @Override
    public String toString() {
        return "Status: " + status + "\n" +
                "Total Results: " + totalResults + "\n" +
                "Articles Count: " + (articles != null ? articles.size() : 0);
    }
}
