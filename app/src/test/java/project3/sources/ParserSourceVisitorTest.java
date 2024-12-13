package project3.sources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project3.parsers.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ParserSourceVisitorTest {

    private static Logger logger;
    private ParserSourceVisitor parserSourceVisitor;

    @BeforeEach
    void setUp() {
        logger = Logger.getLogger(ParserSourceVisitorTest.class.getName());
    }

    @Test
    void testVisitFileSourceSimple() {
        String format = "simple";
        parserSourceVisitor = new ParserSourceVisitor(format, logger);

        FileSource fileSource = new FileSource(Path.of("testPath"));
        Parser parser = fileSource.accept(parserSourceVisitor);

        assertInstanceOf(SimpleParser.class, parser, "Expected SimpleParser for 'simple' format.");
    }

    @Test
    void testVisitFileSourceNewsAPI() {
        String format = "newsapi";
        parserSourceVisitor = new ParserSourceVisitor(format, logger);

        FileSource fileSource = new FileSource(Path.of("testPath"));

        Parser parser = fileSource.accept(parserSourceVisitor);

        assertInstanceOf(NewsAPIParser.class, parser, "Expected NewsAPIParser for 'newsapi' format.");
    }

    @Test
    void testVisitUrlSourceNewsAPI() throws MalformedURLException {
        String format = "newsapi";
        parserSourceVisitor = new ParserSourceVisitor(format, logger);

        UrlSource urlSource = new UrlSource(new URL("https://www.cnet.com/news/nasa-helicopter-took-a-piece-of-the-wright-brothers-famous-plane-to-mars/"), logger);

        Parser parser = urlSource.accept(parserSourceVisitor);

        assertInstanceOf(NewsAPIParser.class, parser, "Expected NewsAPIParser for 'newsapi' format.");
    }

    @Test
    void testVisitUrlSourceInvalidFormat() throws MalformedURLException {
        String format = "invalidFormat";
        parserSourceVisitor = new ParserSourceVisitor(format, logger);

        UrlSource urlSource = new UrlSource(new URL("https://invalid.com"), logger);

        assertThrows(IllegalArgumentException.class, () -> urlSource.accept(parserSourceVisitor),
                "Expected IllegalArgumentException for invalid format.");
    }

    @Test
    void testVisitFileSourceInvalidFormat(){
        String format = "invalidFormat";
        parserSourceVisitor = new ParserSourceVisitor(format, logger);

        FileSource fileSource = new FileSource(Path.of("testPath"));

        assertThrows(IllegalArgumentException.class, () -> fileSource.accept(parserSourceVisitor),
                "Expected IllegalArgumentException for invalid format.");
    }

    @Test
    void testVisitFileSourceEmpty() {
        String format = "simple";
        parserSourceVisitor = new ParserSourceVisitor(format, logger);

        FileSource fileSource = new FileSource(Path.of("emptyFilePath"));

        Parser parser = fileSource.accept(parserSourceVisitor);

        assertInstanceOf(SimpleParser.class, parser, "Expected SimpleParser for 'simple' format.");
    }

    @Test
    void testVisitFileSourceWithNullLogger() {
        String format = "simple";
        parserSourceVisitor = new ParserSourceVisitor(format, null);  // Passing null logger
        FileSource fileSource = new FileSource(Path.of("testPath"));

        assertInstanceOf(SimpleParser.class, fileSource.accept(parserSourceVisitor), "Expected SimpleParser.");
    }

    @Test
    void testCreateSourceInvalidSourceType() {
        String sourceType = "invalidSourceType";
        String format = "simple";
        String location = "someLocation";

        SourceFormatType sourceFormatType = new SourceFormatType(sourceType, format, location);

        assertThrows(IllegalArgumentException.class, sourceFormatType::createSource,
                "Expected IllegalArgumentException for invalid source type.");
    }

    @Test
    void testVisitUrlSourceNullUrl() {
        String format = "newsapi";
        parserSourceVisitor = new ParserSourceVisitor(format, logger);

        UrlSource urlSource = new UrlSource(null, logger);

        assertInstanceOf(NewsAPIParser.class, urlSource.accept(parserSourceVisitor), "Expected NewsAPIParser.");
    }

    @Test
    void testVisitFileSourceInvalidPath() {
        String format = "simple";
        parserSourceVisitor = new ParserSourceVisitor(format, logger);
        FileSource fileSource = new FileSource(Path.of("invalidPath"));

        assertThrows(java.nio.file.NoSuchFileException.class, fileSource::getInputStream,
                "Expected exception for invalid file path.");
    }

    @Test
    void testVisitFileSourceNullPath() {
        String format = "simple";
        parserSourceVisitor = new ParserSourceVisitor(format, logger);
        FileSource fileSource = new FileSource(null);

        assertThrows(NullPointerException.class, fileSource::getInputStream,
                "Expected exception for null file path.");
    }

    @Test
    void testCreateSourceValidFileSource() {
        String sourceType = "file";
        String format = "simple";
        String location = "src/main/resources/simple.json";

        SourceFormatType sourceFormatType = new SourceFormatType(sourceType, format, location);

        assertDoesNotThrow(() -> {
            DataSource dataSource = sourceFormatType.createSource();
            assertNotNull(dataSource, "Expected DataSource for valid file source.");
        });
    }

    @Test
    void testCreateSourceValidUrlSource() {
        String sourceType = "url";
        String format = "newsapi";
        String location = "https://newsapi.org/v2/top-headlines?country=us";

        SourceFormatType sourceFormatType = new SourceFormatType(sourceType, format, location);

        assertDoesNotThrow(() -> {
            DataSource dataSource = sourceFormatType.createSource();
            assertNotNull(dataSource, "Expected DataSource for valid URL source.");
        });
    }

}
