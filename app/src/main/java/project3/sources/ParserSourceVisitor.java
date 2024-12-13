package project3.sources;

import project3.parsers.*;
import java.util.logging.Logger;

/**
 * Visitor implementation to assign parsers based on the source type and format.
 */
public class ParserSourceVisitor implements SourceVisitor {
    private final String format;
    private final Logger logger;

    public ParserSourceVisitor(String format, Logger logger) {
        this.format = format;
        this.logger = logger;
    }

    /**
     * Visits a FileSource and returns the appropriate parser.
     *
     * @param fileSource The file source to visit.
     * @return The parser (SimpleParser or NewsAPIParser) for the file source.
     */
    @Override
    public Parser visit(FileSource fileSource) {
        if ("simple".equalsIgnoreCase(format)) {
            return new SimpleParser(logger);
        } else if ("newsapi".equalsIgnoreCase(format)) {
            return new NewsAPIParser(logger);
        } else {
            throw new IllegalArgumentException("Unsupported format for FileSource: " + format);
        }
    }

    /**
     * Visits a UrlSource and returns the appropriate parser.
     *
     * @param urlSource The URL source to visit.
     * @return The parser (NewsAPIParser) for the URL source.
     */
    @Override
    public Parser visit(UrlSource urlSource) {
        if ("newsapi".equalsIgnoreCase(format)) {
            return new NewsAPIParser(logger);
        } else {
            throw new IllegalArgumentException("Unsupported format for UrlSource: " + format);
        }
    }
}
