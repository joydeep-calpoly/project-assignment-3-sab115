package project3.sources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UrlSourceTest {

    private UrlSource urlSource;

    @Mock
    private HttpURLConnection mockConnection;

    @Mock
    private URL mockUrl;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockUrl.openConnection()).thenReturn(mockConnection);
        urlSource = new UrlSource(mockUrl, Logger.getLogger(UrlSourceTest.class.getName()));
    }

    @Test
    void testGetInputStream() throws Exception {
        // Simulate a successful HTTP response (HTTP_OK)
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        InputStream mockInputStream = mock(InputStream.class);
        when(mockConnection.getInputStream()).thenReturn(mockInputStream);

        InputStream result = urlSource.getInputStream();
        assertNotNull(result);
        verify(mockConnection, times(1)).getInputStream();
    }

    @Test
    void testGetInputStreamWithError() throws Exception {
        // Simulate a HTTP error response (HTTP_BAD_REQUEST)
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);

        // Should throw a RuntimeException when an error occurs
        assertThrows(RuntimeException.class, () -> urlSource.getInputStream());
    }

    @Test
    void testGetInputStreamEmptyResponse() throws Exception {
        // Simulate a successful HTTP response (HTTP_OK)
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

        // Mock an empty InputStream
        InputStream mockInputStream = mock(InputStream.class);
        when(mockInputStream.read()).thenReturn(-1);  // Indicating the stream is empty
        when(mockConnection.getInputStream()).thenReturn(mockInputStream);

        // Call the method under test
        InputStream result = urlSource.getInputStream();

        // Verify that the result is not null
        assertNotNull(result, "InputStream should not be null");

        // Verify that the stream's read method returns -1, indicating it's empty
        assertEquals(-1, result.read(), "InputStream should be empty");

        // Verify the connection's getInputStream was called exactly once
        verify(mockConnection, times(1)).getInputStream();
    }


    @Test
    void testGetInputStreamWithIOException() throws Exception {
        // Simulate a network issue or other error
        when(mockConnection.getResponseCode()).thenThrow(new IOException("Network error"));

        assertThrows(IOException.class, () -> urlSource.getInputStream());
    }
}
