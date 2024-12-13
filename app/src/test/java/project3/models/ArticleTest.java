package project3.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for validating functionality of Article and its derived classes: NewsAPIArticle, SimpleArticle.
 */
class ArticleTest {

    /**
     * Test valid article creation for Article, NewsAPIArticle, and SimpleArticle.
     */
    @ParameterizedTest
    @ValueSource(classes = {Article.class, NewsAPIArticle.class, SimpleArticle.class})
    void testValidArticle(Class<?> articleClass) throws Exception {
        // Prepare input data for constructing an article
        String title = "The latest on the coronavirus pandemic and vaccines: Live updates - CNN";
        String description = "The coronavirus pandemic has brought countries to a standstill. Meanwhile, vaccinations have already started in some countries as cases continue to rise. Follow here for the latest.";
        String url = "https://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html";
        String publishedAt = "2021-03-24T22:32:00Z";

        // Create the article instance dynamically using reflection
        Article article = (Article) articleClass.getConstructor(String.class, String.class, String.class, String.class)
                .newInstance(title, description, url, publishedAt);

        // Assert that the article is valid
        assertTrue(article.isValid(), "Article should be valid when all fields are provided.");
    }

    /**
     * Test invalid article creation for missing fields (like title).
     */
    @ParameterizedTest
    @ValueSource(classes = {Article.class, NewsAPIArticle.class, SimpleArticle.class})
    void testInvalidArticle(Class<?> articleClass) throws Exception {
        // Prepare input data for constructing an article with missing title
        String description = "The coronavirus pandemic has brought countries to a standstill.";
        String url = "https://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html";
        String publishedAt = "2021-03-24T22:32:00Z";

        // Create the article instance dynamically with a null title
        Article article = (Article) articleClass.getConstructor(String.class, String.class, String.class, String.class)
                .newInstance(null, description, url, publishedAt);

        // Assert that the article is invalid due to the missing title
        assertFalse(article.isValid(), "Article should be invalid when title is missing.");
    }

    /**
     * Test for an invalid article with missing URL.
     */
    @ParameterizedTest
    @ValueSource(classes = {Article.class, NewsAPIArticle.class, SimpleArticle.class})
    void testInvalidArticleMissingUrl(Class<?> articleClass) throws Exception {
        // Prepare input data for constructing an article with missing URL
        String title = "The latest on the coronavirus pandemic and vaccines: Live updates - CNN";
        String description = "The coronavirus pandemic has brought countries to a standstill.";
        String publishedAt = "2021-03-24T22:32:00Z";

        // Create the article instance dynamically with a null URL
        Article article = (Article) articleClass.getConstructor(String.class, String.class, String.class, String.class)
                .newInstance(title, description, null, publishedAt);

        // Assert that the article is invalid due to the missing URL
        assertFalse(article.isValid(), "Article should be invalid when URL is missing.");
    }

    /**
     * Test for valid article format and all fields for NewsAPIArticle.
     */
    @Test
    void testValidNewsAPIArticle() {
        NewsAPIArticle.Source source = new NewsAPIArticle.Source("cnn", "CNN");

        NewsAPIArticle article = new NewsAPIArticle(
                "The latest on the coronavirus pandemic and vaccines: Live updates - CNN",
                "The coronavirus pandemic has brought countries to a standstill. Meanwhile, vaccinations have already started in some countries as cases continue to rise.",
                "https://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html",
                "2021-03-24T22:32:00Z",
                "cnn", "CNN", null , source);

        // Assert the article is valid
        assertTrue(article.isValid(), "NewsAPIArticle should be valid with all fields provided.");
    }

    /**
     * Test for missing fields in NewsAPIArticle (e.g., missing title).
     */
    @Test
    void testInvalidNewsAPIArticle() {
        NewsAPIArticle.Source source = new NewsAPIArticle.Source("cnn", "CNN");

        NewsAPIArticle article = new NewsAPIArticle(
                null,
                "The coronavirus pandemic has brought countries to a standstill.",
                "https://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html",
                "2021-03-24T22:32:00Z",
                "cnn", "CNN", null , source);

        // Assert the article is invalid due to missing title
        assertFalse(article.isValid(), "NewsAPIArticle should be invalid when title is missing.");
    }

    /**
     * Test for valid SimpleArticle with all required fields.
     */
    @Test
    void testValidSimpleArticle() {
        SimpleArticle article = new SimpleArticle(
                "Assignment #2",
                "Extend Assignment #1 to support multiple sources and to introduce source processor.",
                "https://canvas.calpoly.edu/courses/55411/assignments/274503",
                "2021-04-16 09:53:23.709229");

        // Assert the article is valid
        assertTrue(article.isValid(), "SimpleArticle should be valid when all fields are provided.");
    }

    /**
     * Test for invalid SimpleArticle with missing title.
     */
    @Test
    void testInvalidSimpleArticle() {
        SimpleArticle article = new SimpleArticle(
                null,
                "Extend Assignment #1 to support multiple sources and to introduce source processor.",
                "https://canvas.calpoly.edu/courses/55411/assignments/274503",
                "2021-04-16 09:53:23.709229");

        // Assert the article is invalid due to missing title
        assertFalse(article.isValid(), "SimpleArticle should be invalid when title is missing.");
    }

    /**
     * Test for SimpleArticle with missing URL field.
     */
    @Test
    void testInvalidSimpleArticleMissingUrl() {
        SimpleArticle article = new SimpleArticle(
                "Assignment #2",
                "Extend Assignment #1 to support multiple sources and to introduce source processor.",
                null,
                "2021-04-16 09:53:23.709229");

        // Assert the article is invalid due to missing URL
        assertFalse(article.isValid(), "SimpleArticle should be invalid when URL is missing.");
    }
}
