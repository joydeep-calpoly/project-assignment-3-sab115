package project3.parsers;

import project3.models.Article;
import java.io.InputStream;
import java.util.List;

public interface Parser {

    /**
     * Parses the input stream and converts it into a list of {@link Article} objects.
     *
     * @param input The input stream to be parsed.
     * @return A list of {@link Article} objects parsed from the input stream.
     */
    List<Article> parse(InputStream input);
}
