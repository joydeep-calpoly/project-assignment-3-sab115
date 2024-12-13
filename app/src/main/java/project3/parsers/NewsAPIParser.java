package project3.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import project3.models.NewsAPIArticle;
import project3.models.NewsAPIResponse;
import project3.models.Article;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A parser for parsing NewsAPI response JSON data.
 * It converts the JSON response into a list of valid {@link NewsAPIArticle} objects.
 */
public class NewsAPIParser implements Parser {
    private final Logger logger;

    public NewsAPIParser(Logger logger) {
        this.logger = logger;
    }

    /**
     * Parses the input stream containing the NewsAPI JSON response and converts it into a list of {@link Article} objects.
     *
     * @param input The input stream containing the NewsAPI JSON response.
     * @return A list of valid {@link Article} objects parsed from the response.
     */
    @Override
    public List<Article> parse(InputStream input) {
        List<Article> articles = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            NewsAPIResponse response = objectMapper.readValue(input, NewsAPIResponse.class);

            if (!"ok".equalsIgnoreCase(response.getStatus())) {
                logger.warning("API returned an error status: " + response.getStatus());
                return articles;
            }

            for (NewsAPIArticle article : response.getArticles()) {
                if (article.isValid()) {
                    articles.add(article);
                } else {
                    logger.warning("Invalid NewsAPI article: missing required fields.");
                }
            }
        } catch (Exception e) {
            logger.warning("Failed to parse NewsAPI JSON: " + e.getMessage());
        }

        return articles;
    }
}
