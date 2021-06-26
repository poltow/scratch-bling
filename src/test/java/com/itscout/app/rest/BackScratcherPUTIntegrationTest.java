package com.itscout.app.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.itscout.Application;
import com.itscout.domain.model.BackScratcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class BackScratcherPUTIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test() throws URISyntaxException, IOException {

        String url = "http://localhost:" + port + "/backscratchers/";
        ObjectMapper objectMapper = new ObjectMapper();

        // BackScratcher table is empty
        ResponseEntity<BackScratcher[]> backScratchersResponse = restTemplate.getForEntity(new URI(url), BackScratcher[].class);
        assertTrue(backScratchersResponse.getBody().length == 0);

        // Insert one backScratcher
        ResponseEntity<String> result = restTemplate.postForEntity(new URI(url),
                new HttpEntity<>(new BackScratcher(-1L, "NAME", "DESC", new String[]{"S", "L"}, 10.20d)), String.class);
        assertEquals(201, result.getStatusCodeValue());
        BackScratcher backScratcher = objectMapper.readValue(result.getBody(), BackScratcher.class);

        // Name is mandatory
        backScratcher.setName("");
        assertEquals(400, restTemplate.exchange(new URI(url + backScratcher.getId()), HttpMethod.PUT,
                new HttpEntity<>(backScratcher), BackScratcher.class).getStatusCodeValue());

        backScratcher.setName(null);
        assertEquals(400, restTemplate.exchange(new URI(url + backScratcher.getId()), HttpMethod.PUT,
                new HttpEntity<>(backScratcher), BackScratcher.class).getStatusCodeValue());
        backScratcher.setName("NAME");

        // Description is mandatory
        backScratcher.setDescription(null);
        assertEquals(400, restTemplate.exchange(new URI(url + backScratcher.getId()), HttpMethod.PUT,
                new HttpEntity<>(backScratcher), BackScratcher.class).getStatusCodeValue());

        backScratcher.setDescription("");
        assertEquals(400, restTemplate.exchange(new URI(url + backScratcher.getId()), HttpMethod.PUT,
                new HttpEntity<>(backScratcher), BackScratcher.class).getStatusCodeValue());
        backScratcher.setDescription("DESC");

        // Price should be positive
        backScratcher.setPrice(-1d);
        assertEquals(400, restTemplate.exchange(new URI(url + backScratcher.getId()), HttpMethod.PUT,
                new HttpEntity<>(backScratcher), BackScratcher.class).getStatusCodeValue());

        backScratcher.setPrice(0d);
        assertEquals(400, restTemplate.exchange(new URI(url + backScratcher.getId()), HttpMethod.PUT,
                new HttpEntity<>(backScratcher), BackScratcher.class).getStatusCodeValue());
        backScratcher.setPrice(20d);

        // Sizes should not be null
        backScratcher.setSizes(null);
        assertEquals(400, restTemplate.exchange(new URI(url + backScratcher.getId()), HttpMethod.PUT,
                new HttpEntity<>(backScratcher), BackScratcher.class).getStatusCodeValue());

        // Sizes should not be empty
        backScratcher.setSizes(new String[]{});
        assertEquals(400, restTemplate.exchange(new URI(url + backScratcher.getId()), HttpMethod.PUT,
                new HttpEntity<>(backScratcher), BackScratcher.class).getStatusCodeValue());
        backScratcher.setSizes(new String[]{"S", "L"});

        // Sizes should  be valid
        backScratcher.setSizes(new String[]{"XXX"});
        assertEquals(400, restTemplate.exchange(new URI(url + backScratcher.getId()), HttpMethod.PUT,
                new HttpEntity<>(backScratcher), BackScratcher.class).getStatusCodeValue());
        backScratcher.setSizes(new String[]{"S", "L"});

        //clean DB
        restTemplate.delete(new URI(url + backScratcher.getId()));

        //assert is clean
        backScratchersResponse = restTemplate.getForEntity(new URI(url), BackScratcher[].class);
        assertTrue(backScratchersResponse.getBody().length == 0);
    }
}