package project3.parsers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import project3.models.Article;
import project3.models.SimpleArticle;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class SimpleParserTest {
    private static SimpleParser parser;

    @BeforeAll
    static void setup() {
        parser = new SimpleParser(Logger.getLogger(SimpleParserTest.class.getName()));
    }

    @Test
    void testParseValidSimpleJson() {
        String validJson = """
            {
                "title": "Test Article",
                "description": "This is a test article.",
                "publishedAt": "2024-11-17T10:00:00Z",
                "url": "https://example.com"
            }
        """;
        InputStream input = new ByteArrayInputStream(validJson.getBytes());
        List<Article> articles = parser.parse(input);

        assertEquals(1, articles.size());
        assertTrue(articles.get(0).isValid());
    }

    @Test
    void testParseInvalidSimpleJson() {
        String invalidJson = """
            {
                "title": "Incomplete Article"
            }
        """;
        InputStream input = new ByteArrayInputStream(invalidJson.getBytes());
        List<Article> articles = parser.parse(input);

        assertTrue(articles.isEmpty());
    }

    @Test
    void testParseEmptyArticleList() {
        String emptyJson = "{}";
        InputStream input = new ByteArrayInputStream(emptyJson.getBytes());
        List<Article> articles = parser.parse(input);

        assertTrue(articles.isEmpty());
    }

    @Test
    void testParseMalformedJson() {
        String malformedJson = """
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
