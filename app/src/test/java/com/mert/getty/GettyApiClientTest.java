package com.mert.getty;

import com.mert.getty.data.GettyService;
import com.mert.getty.data.api.GettyApiInterceptor;
import com.mert.getty.data.model.GettyResponse;
import com.mert.getty.data.model.Image;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Mert Kilic on 20.7.2017.
 */
@RunWith(JUnit4.class)
public class GettyApiClientTest extends MockWebServerTest {

    private GettyService service;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        String endpoint = getBaseEndpoint();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(endpoint)
                .client(new OkHttpClient.Builder().addInterceptor(new GettyApiInterceptor()).build())
                .build();

        service = retrofit.create(GettyService.class);
    }

    @Test
    public void checkApiKey() throws IOException, InterruptedException {
        enqueueResponse("search-response.json");
        service.search("hair", 1, 1).execute();
        assertRequestContainsHeader("Api-Key", BuildConfig.GETTY_API_KEY);
    }

    @Test
    public void checkRequestSentToCorrectEndPoint() throws IOException, InterruptedException {
        enqueueResponse(200);
        service.search("hair", 1, 1).execute();
        assertGetRequestSentTo("/search/images?phrase=hair&page_size=1&page=1");
    }

    @Test
    public void search() throws IOException, InterruptedException {
        enqueueResponse("search-response.json");
        GettyResponse response = service.search("hair", 1, 1).execute().body();
        assertNotNull(response);
        assertThat(response.getResultCount(), is(1));
        Image image = response.getImages().get(0);
        assertNotNull(image);
        assertEquals(image.getUrl(), "https://media.gettyimages.com/photos/afgan-hound-with-hair-blowing-studio-shot-picture-idsb10069853n-001?b=1&k=6&m=sb10069853n-001&s=170x170&h=FpW3R52LBELROoHmBQAROq6c0XUbJLQc6qTESAfWtt0=");
        assertNull(image.getCaption());
        assertEquals(image.getTitle(), "Afgan Hound with hair blowing, studio shot");
        assertEquals(image.getId(), "sb10069853n-001");
    }

}
