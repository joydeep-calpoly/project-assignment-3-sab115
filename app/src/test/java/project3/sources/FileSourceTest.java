package project3.sources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class FileSourceTest {
    private static final Logger logger = Logger.getLogger(FileSourceTest.class.getName());
    private FileSource fileSource;
    private Path tempFile;

    @BeforeEach
    void setUp() throws Exception {
        // Create a temporary file for testing
        tempFile = Files.createTempFile("test", ".json");
        fileSource = new FileSource(tempFile);
    }

    @Test
    void testGetInputStreamValidFile() throws Exception {
        // Write a simple valid JSON string into the file
        String jsonContent = "{\"title\":\"Test Article\",\"description\":\"Test Description\"}";
        Files.write(tempFile, jsonContent.getBytes());

        InputStream inputStream = fileSource.getInputStream();
        assertNotNull(inputStream);

        // Verify we can read the content correctly
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        assertNotNull(line);
        assertTrue(line.contains("Test Article"));
    }

    @Test
    void testGetInputStreamFileNotFound() {
        Path nonexistentFile = Paths.get("nonexistent_file.json");
        fileSource = new FileSource(nonexistentFile);

        // Should throw an exception when trying to read a nonexistent file
        assertThrows(IOException.class, () -> fileSource.getInputStream());
    }

    @Test
    void testGetInputStreamEmptyFile() throws Exception {
        // Create an empty file
        Path emptyFile = Files.createTempFile("empty", ".json");
        fileSource = new FileSource(emptyFile);

        InputStream inputStream = fileSource.getInputStream();
        assertNotNull(inputStream);

        // Read the file and verify it's empty
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        assertNull(reader.readLine());  // Should be empty
    }

    @Test
    void testGetInputStreamWithException() throws Exception {
        // Simulate an exception by trying to create a file source with a path that is not a valid file
        Path invalidPath = Paths.get("/invalid/path/to/file.json");
        fileSource = new FileSource(invalidPath);

        // Should throw an exception as file doesn't exist
        assertThrows(IOException.class, () -> fileSource.getInputStream());
    }
}
