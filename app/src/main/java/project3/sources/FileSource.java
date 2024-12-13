package project3.sources;

import project3.parsers.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSource implements DataSource {
    private final String fileName;

    public FileSource(String fileName) throws IllegalArgumentException {
        this.fileName = fileName;

        if (!fileExists()) {
            throw new IllegalArgumentException("The specified file does not exist: " + fileName);
        }
    }

    private boolean fileExists() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            assert fileName != null;
            Path path = Paths.get(fileName);
            return Files.exists(path);
        }
        return true;
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
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            // If not found, try loading from filesystem
            Path path = Paths.get(fileName);
            if (Files.exists(path)) {
                inputStream = Files.newInputStream(path);
            }
        }

        if (inputStream == null) {
            throw new IOException("Cannot open input stream for file: " + fileName);
        }

        return inputStream;
    }
}
