package project3.sources;

import project3.parsers.Parser;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileSource implements DataSource {
    private final Path filePath;

    public FileSource(String fileName) throws Exception {
        // First, try to load the resource from the classpath (for bundled resources)
        InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(fileName);

        if (resourceStream != null) {
            // If the resource is found, use it
            this.filePath = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI());
        } else {
            // If the resource is not found, use the file system path
            this.filePath = Paths.get(fileName);
        }
    }

    @Override
    public Parser accept(SourceVisitor visitor) {
        return visitor.visit(this);
    }

    /**
     * Fetches the input stream for reading from the specified file.
     *
     * @return An {@link InputStream} for the file.
     * @throws Exception If an error occurs while opening the file.
     */
    @Override
    public InputStream getInputStream() throws Exception {
        return Files.newInputStream(filePath);
    }

    public Path getFilePath() {
        return filePath;
    }
}
