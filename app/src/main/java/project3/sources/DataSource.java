package project3.sources;

import project3.parsers.Parser;

import java.io.InputStream;

/**
 * Interface for sources that can accept a visitor to assign parsers and fetch input streams.
 */
public interface DataSource {
    /**
     * Accepts a visitor to dynamically determine the appropriate parser.
     *
     * @param visitor The visitor determining the parser.
     * @return The parser for this source.
     */
    Parser accept(SourceVisitor visitor);

    /**
     * Fetches the input stream for this source.
     *
     * @return The input stream for reading data.
     * @throws Exception If an error occurs while fetching the input stream.
     */
    InputStream getInputStream() throws Exception;
}
