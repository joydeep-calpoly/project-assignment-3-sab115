package project3.parsers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import project3.models.Article;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class NewsAPIParserTest {
    private static NewsAPIParser parser;

    @BeforeAll
    static void setup() {
        parser = new NewsAPIParser(Logger.getLogger(NewsAPIParserTest.class.getName()));
    }

    @Test
    void testParseValidNewsAPIJson() {
        String validJson = """
            {
                "status": "ok",
                "totalResults": 1,
                "articles": [
                    {
                        "title": "NewsAPI Article",
                        "description": "A sample NewsAPI article.",
                        "publishedAt": "2024-11-17T10:00:00Z",
                        "url": "https://newsapi.com",
                        "source": {"id": "cnn", "name": "CNN"}
                    }
                ]
            }
        """;
        InputStream input = new ByteArrayInputStream(validJson.getBytes());
        List<Article> articles = parser.parse(input);

        assertEquals(1, articles.size());
        assertTrue(articles.get(0).isValid());
    }

    @Test
    void testParseInvalidNewsAPIJson() {
        String invalidJson = """
            {
                "status": "ok",
                "totalResults": 1,
                "articles": [
                    {
                        "title": "Incomplete Article"
                    }
                ]
            }
        """;
        InputStream input = new ByteArrayInputStream(invalidJson.getBytes());
        List<Article> articles = parser.parse(input);

        assertTrue(articles.isEmpty());
    }

    @Test
    void testParseEmptyArticleList() {
        String emptyArticlesJson = """
            {
                "status": "ok",
                "totalResults": 0,
                "articles": []
            }
        """;
        InputStream input = new ByteArrayInputStream(emptyArticlesJson.getBytes());
        List<Article> articles = parser.parse(input);

        assertEquals(0, articles.size());
    }

    @Test
    void testParseMalformedJson() {
        String malformedJson = """
            {
                "status": "ok",
                "totalResults": 1,
                "articles": [
                    {
                        "title": "Malformed Article",
                        "description": "Missing some fields"
                        // Missing closing bracket
        """;
        InputStream input = new ByteArrayInputStream(malformedJson.getBytes());
        List<Article> articles = parser.parse(input);

        assertTrue(articles.isEmpty());
    }

    @Test
    void testParseLargeJson() throws Exception {
        StringBuilder largeJson = new StringBuilder();
        largeJson.append("{ \"status\": \"ok\", \"totalResults\": 1000, \"articles\": [");

        // Generate 1000 valid articles
        for (int i = 0; i < 1000; i++) {
            largeJson.append("{ \"title\": \"Article " + i + "\", \"description\": \"Description " + i + "\", \"publishedAt\": \"2024-11-17T10:00:00Z\", \"url\": \"https://example.com/article" + i + "\" },");
        }

        // Remove the last comma
        largeJson.setLength(largeJson.length() - 1);
        largeJson.append("]}");

        InputStream input = new ByteArrayInputStream(largeJson.toString().getBytes());
        List<Article> articles = parser.parse(input);

        // Assert that 1000 articles were parsed
        assertEquals(1000, articles.size());
    }

    @Test
    void testParseUnexpectedStructure() throws Exception {
        String unexpectedJson = """
        {
            "status": "ok",
            "totalResults": 1,
            "articles": [
                {
                    "unknownField": "some value"
                }
            ]
        }
    """;
        InputStream input = new ByteArrayInputStream(unexpectedJson.getBytes());
        List<Article> articles = parser.parse(input);

        assertTrue(articles.isEmpty());
    }

}
