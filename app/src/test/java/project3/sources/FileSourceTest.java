package project3.sources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class FileSourceTest {
    private static final Logger logger = Logger.getLogger(FileSourceTest.class.getName());
    private FileSource fileSource;
    private Path tempFile;

    @BeforeEach
    void setUp() throws Exception {
        fileSource = new FileSource("test.json");
    }

    @Test
    void testGetInputStreamValidFile() throws Exception {
        // Assuming 'test.json' exists in src/test/resources
        fileSource = new FileSource("test.json");

        InputStream inputStream = fileSource.getInputStream();
        assertNotNull(inputStream);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        assertNotNull(line);
    }

    @Test
    void testGetInputStreamEmptyFile() throws Exception {
        fileSource = new FileSource("empty.json");

        InputStream inputStream = fileSource.getInputStream();
        assertNotNull(inputStream);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        assertNull(reader.readLine(), "Empty file should not contain any lines.");
    }

    @Test
    void testGetInputStreamClasspathResource() throws Exception {
        String resourceLocation = "simple.json";
        fileSource = new FileSource(resourceLocation);

        InputStream inputStream = fileSource.getInputStream();
        assertNotNull(inputStream);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        assertNotNull(line);
    }

    @Test
    void testVisitFileSourceValid() throws Exception {
        // This file should exist in resources
        String fileName = "test.json";
        fileSource = new FileSource(fileName);

        assertNotNull(fileSource.getInputStream(), "File input stream should not be null.");
    }

    @Test
    void testVisitFileSourceInvalid() {
        // Test for a non-existent file
        String fileName = "nonexistentFile.json";  // Invalid file
        assertThrows(IllegalArgumentException.class, () -> new FileSource(fileName), "IllegalArgumentException expected for non-existent file");
    }
}
