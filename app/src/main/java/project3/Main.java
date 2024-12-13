package project3;

import project3.models.Article;
import project3.sources.*;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            new File("build/logs").mkdirs();
            new File("build/logs/logs.out").createNewFile();
            Logger logger = Logger.getGlobal();
            FileHandler fileHandler = new FileHandler("build/logs/logs.out", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            ParserSourceVisitor visitor = new ParserSourceVisitor("simple", logger);

            DataSource simpleSource = new FileSource("simple.json");
            processSource(simpleSource, visitor);

            visitor = new ParserSourceVisitor("newsapi", logger);

            DataSource newsApiSource = new FileSource("newsapi.json");
            processSource(newsApiSource, visitor);

            String apiKey = System.getenv("NEWS_API_KEY");
            if (apiKey == null) {
                logger.severe("Missing \"NEWS_API_KEY\" environment variable");
                return;
            }
            URI uri = URI.create("https://newsapi.org/v2/top-headlines?country=us&apiKey=" + apiKey);
            DataSource urlSource = new UrlSource(uri.toURL(), logger);
            processSource(urlSource, visitor);

        } catch (Exception e) {
            logger.severe("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Processes a given data source by accepting the appropriate visitor and parsing the input stream.
     *
     * @param source The data source (either FileSource or UrlSource).
     * @param visitor The visitor that determines the parser to use.
     */
    private static void processSource(DataSource source, ParserSourceVisitor visitor) throws Exception {
        var parser = source.accept(visitor);

        try (InputStream input = source.getInputStream()) {
            List<Article> articles = parser.parse(input);
            articles.forEach(article -> System.out.println(article.toString() + "\n"));
        }
    }
}
