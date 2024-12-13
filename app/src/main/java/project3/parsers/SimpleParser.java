package project3.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import project3.models.SimpleArticle;
import project3.models.Article;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SimpleParser implements Parser {
    private final Logger logger;

    public SimpleParser(Logger logger) {
        this.logger = logger;
    }

    /**
     * Parses the input stream containing the simplified JSON article data and converts it into a list of {@link Article} objects.
     *
     * @param input The input stream containing the simplified JSON data.
     * @return A list of valid {@link Article} objects parsed from the data.
     */
    @Override
    public List<Article> parse(InputStream input) {
        List<Article> articles = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            SimpleArticle article = objectMapper.readValue(input, SimpleArticle.class);
            if (article.isValid()) {
                articles.add(article);
            } else {
                logger.warning("Invalid article: missing required fields.");
            }
        } catch (Exception e) {
            logger.warning("Failed to parse simple JSON: " + e.getMessage());
        }

        return articles;
    }
}
