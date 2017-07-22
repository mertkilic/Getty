package com.mert.getty;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okio.BufferedSource;
import okio.Okio;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Mert Kilic on 20.7.2017.
 */

public class MockWebServerTest {

    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        this.server = new MockWebServer();
        this.server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    protected String getBaseEndpoint() {
        return server.url("/").toString();
    }

    protected void assertRequestSentTo(String url) throws InterruptedException {
        RecordedRequest request = server.takeRequest();
        assertEquals(url, request.getPath());
    }

    protected void assertGetRequestSentTo(String url) throws InterruptedException {
        RecordedRequest request = server.takeRequest();
        assertEquals(url, request.getPath());
        assertEquals("GET", request.getMethod());
    }

    void assertRequestContainsHeader(String key, String expectedValue)
            throws InterruptedException {
        RecordedRequest recordedRequest = server.takeRequest();
        String value = recordedRequest.getHeader(key);
        assertNotNull(value);
        assertEquals(expectedValue, value);
    }

    void enqueueResponse(int code) throws IOException {
        MockResponse response = new MockResponse();
        response.setResponseCode(code);
        response.setBody("{}");
        server.enqueue(response);
    }

    void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.<String, String>emptyMap());
    }

    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("api-response/" + fileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            mockResponse.addHeader(header.getKey(), header.getValue());
        }
        server.enqueue(mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8)));
    }
}
