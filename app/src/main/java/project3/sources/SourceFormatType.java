package project3.sources;

import java.io.File;
import java.net.URI;
import java.net.URL;

/**
 * Represents the configuration for a data source and its format.
 * Dynamically creates the appropriate source based on the type (file or URL).
 */
public class SourceFormatType {
    private final String sourceType;
    private final String format;
    private final String location;

    /**
     * Constructor for SourceFormatType.
     *
     * @param sourceType The source type (file or url).
     * @param format The format of the source (simple or newsapi).
     * @param location The location of the source (file name or URL).
     */
    public SourceFormatType(String sourceType, String format, String location) {
        this.sourceType = sourceType;
        this.format = format;
        this.location = location;
    }

    public String getFormat() {
        return format;
    }

    /**
     * Dynamically creates a DataSource based on the sourceType.
     *
     * @return A DataSource instance (FileSource or UrlSource).
     */
    public DataSource createSource() throws Exception {
        if ("file".equalsIgnoreCase(sourceType)) {
            URL resource = getClass().getClassLoader().getResource(location);
            if (resource != null) {
                if ("file".equals(resource.getProtocol())) {
                    return new FileSource(new File(resource.toURI()).getAbsolutePath());
                } else {
                    throw new IllegalArgumentException("Resource is not a file: " + resource);
                }
            } else {
                return new FileSource(location);
            }
        } else if ("url".equalsIgnoreCase(sourceType)) {
            URI uri = URI.create(location);
            URL url = uri.toURL();
            return new UrlSource(url, null);
        } else {
            throw new IllegalArgumentException("Invalid source type: " + sourceType);
        }
    }
}
