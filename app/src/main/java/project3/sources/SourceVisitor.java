package project3.sources;

import project3.parsers.Parser;

public interface SourceVisitor {
    /**
     * Visits a file source and returns the associated parser.
     *
     * @param fileSource The file source to visit.
     * @return The parser for the file source.
     */
    Parser visit(FileSource fileSource);

    /**
     * Visits a URL source and returns the associated parser.
     *
     * @param urlSource The URL source to visit.
     * @return The parser for the URL source.
     */
    Parser visit(UrlSource urlSource);
}
