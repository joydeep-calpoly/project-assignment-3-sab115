package project3.sources;

import project3.parsers.Parser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class UrlSource implements DataSource {
    private final URL url;
    private final Logger logger;

    public UrlSource(URL url, Logger logger) {
        this.url = url;
        this.logger = logger;
    }

    @Override
    public Parser accept(SourceVisitor visitor) {
        return visitor.visit(this);
    }

    /**
     * Fetches the input stream from the URL by making an HTTP GET request.
     *
     * @return The input stream for the fetched data.
     * @throws Exception If an error occurs while opening the connection or fetching the data.
     */
    @Override
    public InputStream getInputStream() throws Exception {
        logger.info("Fetching data from URL: " + url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed to fetch data: HTTP " + responseCode);
        }

        return connection.getInputStream();
    }

    public URL getUrl() {
        return url;
    }
}
