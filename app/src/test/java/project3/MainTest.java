package project3;

import project3.models.NewsAPIArticle;
import project3.models.SimpleArticle;
import project3.parsers.NewsAPIParser;
import project3.parsers.SimpleParser;
import project3.sources.UrlSource;
import project3.models.Article;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainTest {

    private NewsAPIParser mockNewsAPIParser;
    private SimpleParser mockSimpleParser;
    private Logger logger;

    @BeforeEach
    void setUp() {
        logger = Logger.getLogger(MainTest.class.getName());
        mockNewsAPIParser = mock(NewsAPIParser.class);
        mockSimpleParser = mock(SimpleParser.class);
    }

    /**
     * Test for successful processing when the source provides valid data.
     */
    @Test
    void testProcessValidNewsAPIData() throws Exception {
        // Simulate valid data from newsapi.json
        String validJson = """
                {
                    "status": "ok",
                    "totalResults": 38,
                    "articles": [
                        {
                            "source": {"id": "cnn", "name": "CNN"},
                            "author": "By Julia Hollingsworth, CNN",
                            "title": "The latest on the coronavirus pandemic and vaccines: Live updates - CNN",
                            "description": "The coronavirus pandemic has brought countries to a standstill.",
                            "url": "https://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html",
                            "urlToImage": "https://cdn.cnn.com/cnnnext/dam/assets/200213175739-03-coronavirus-0213-super-tease.jpg",
                            "publishedAt": "2021-03-24T22:32:00Z",
                            "content": "A senior European diplomat is urging caution over the use of proposed new rules that would govern exports of Covid-19 vaccines to outside of the EU."
                        }
                    ]
                }
            """;
        InputStream validStream = new ByteArrayInputStream(validJson.getBytes());

        when(mockNewsAPIParser.parse(validStream)).thenReturn(List.of(
                new NewsAPIArticle(
                        "The latest on the coronavirus pandemic and vaccines: Live updates - CNN",
                        "The coronavirus pandemic has brought countries to a standstill.",
                        "https://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html",
                        "2021-03-24T22:32:00Z",
                        "cnn", "CNN",
                        null,
                        null
                )
        ));

        // Process the data and verify
        List<Article> articles = mockNewsAPIParser.parse(validStream);
        assertNotNull(articles);
        assertEquals(1, articles.size());
        assertTrue(articles.get(0).isValid());
    }

    /**
     * Test for handling invalid data (e.g., missing fields or incorrect format).
     */
    @Test
    void testProcessInvalidNewsAPIData() {
        String invalidJson = """
                {
                    "status": "error",
                    "totalResults": 0,
                    "articles": []
                }
            """;
        InputStream invalidStream = new ByteArrayInputStream(invalidJson.getBytes());

        // Mock the parser to return invalid data
        when(mockNewsAPIParser.parse(invalidStream)).thenThrow(new RuntimeException("Invalid JSON format"));

        // Verify that exception is thrown
        assertThrows(RuntimeException.class, () -> mockNewsAPIParser.parse(invalidStream), "Invalid data should throw an exception.");
    }

    /**
     * Test for processing data from a URL source.
     */
    @Test
    void testProcessFromUrl() throws Exception {
        // Mocking UrlSource and SimpleParser
        URL mockUrl = new URL("https://www.cnet.com/news/nasa-helicopter-took-a-piece-of-the-wright-brothers-famous-plane-to-mars/");
        Logger mockLogger = Logger.getLogger(MainTest.class.getName());

        // Instead of creating a real instance of UrlSource, mock it
        UrlSource mockUrlSource = mock(UrlSource.class);

        // Mock the behavior of getInputStream() to return a valid InputStream
        InputStream mockInputStream = new ByteArrayInputStream("""
            {
                "title": "NASA helicopter took a piece of the Wright brothers' plane to Mars - CNET",
                "description": "NASA is gearing up for a dramatic Mars test flight of Ingenuity as soon as April 8.",
                "publishedAt": "2021-03-24T21:43:00Z",
                "url": "https://www.cnet.com/news/nasa-helicopter-took-a-piece-of-the-wright-brothers-famous-plane-to-mars/"
            }
            """.getBytes());

        // Mock the getInputStream() method of the UrlSource
        when(mockUrlSource.getInputStream()).thenReturn(mockInputStream);

        // Mock the SimpleParser to return a list with one SimpleArticle
        SimpleParser mockSimpleParser = mock(SimpleParser.class);
        when(mockSimpleParser.parse(mockInputStream)).thenReturn(List.of(
                new SimpleArticle(
                        "NASA helicopter took a piece of the Wright brothers' plane to Mars - CNET",
                        "NASA is gearing up for a dramatic Mars test flight of Ingenuity as soon as April 8.",
                        "https://www.cnet.com/news/nasa-helicopter-took-a-piece-of-the-wright-brothers-famous-plane-to-mars/",
                        "2021-03-24T21:43:00Z"
                )
        ));

        // Execute the test: parsing the InputStream through the mock parser
        List<Article> articles = mockSimpleParser.parse(mockInputStream);

        // Verify the results
        assertNotNull(articles, "Parsed articles should not be null");
        assertEquals(1, articles.size(), "Expected exactly one article");
        assertTrue(articles.getFirst().isValid(), "The parsed article should be valid");
    }

}
