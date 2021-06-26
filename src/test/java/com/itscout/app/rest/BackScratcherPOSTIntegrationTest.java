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
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class BackScratcherPOSTIntegrationTest {

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

        // Name is mandatory
        ResponseEntity<String> result = restTemplate.postForEntity(new URI(url),
                new HttpEntity<>(new BackScratcher(-1L, "", "DESC", new String[]{"S", "L"}, 10.20d)), String.class);
        assertEquals(400, result.getStatusCodeValue());

        result = restTemplate.postForEntity(new URI(url),
                new HttpEntity<>(new BackScratcher(-1L, null, "DESC", new String[]{"S", "L"}, 10.20d)), String.class);
        assertEquals(400, result.getStatusCodeValue());

        // Description is mandatory
        result = restTemplate.postForEntity(new URI(url),
                new HttpEntity<>(new BackScratcher(-1L, "", "DESC", new String[]{"S", "L"}, 10.20d)), String.class);
        assertEquals(400, result.getStatusCodeValue());

        result = restTemplate.postForEntity(new URI(url),
                new HttpEntity<>(new BackScratcher(-1L, "NAME", null, new String[]{"S", "L"}, 10.20d)), String.class);
        assertEquals(400, result.getStatusCodeValue());

        // Price should be positive or zero
        result = restTemplate.postForEntity(new URI(url),
                new HttpEntity<>(new BackScratcher(-1L, "NAME", "DESC", new String[]{"S", "L"}, -10.20d)), String.class);
        assertEquals(400, result.getStatusCodeValue());

        // Price should be positive
        result = restTemplate.postForEntity(new URI(url),
                new HttpEntity<>(new BackScratcher(-1L, "NAME", "DESC", new String[]{"S", "L"}, 0d)), String.class);
        assertEquals(400, result.getStatusCodeValue());

        // Sizes should be valid
        result = restTemplate.postForEntity(new URI(url),
                new HttpEntity<>(new BackScratcher(-1L, "NAME", "DESC", new String[]{"H", "X"}, 10.20d)), String.class);
        assertEquals(400, result.getStatusCodeValue());

        result = restTemplate.postForEntity(new URI(url),
                new HttpEntity<>(new BackScratcher(-1L, "NAME", "DESC", new String[]{}, 10.20d)), String.class);
        assertEquals(400, result.getStatusCodeValue());

        result = restTemplate.postForEntity(new URI(url),
                new HttpEntity<>(new BackScratcher(-1L, "NAME", "DESC", null, 10.20d)), String.class);
        assertEquals(400, result.getStatusCodeValue());

        // Name is unique
        result = restTemplate.postForEntity(new URI(url),
                new HttpEntity<>(new BackScratcher(-1L, "NAME", "DESC", new String[]{"S", "L"}, 10.20d)), String.class);
        assertEquals(201, result.getStatusCodeValue());
        Long id = objectMapper.readValue(result.getBody(), BackScratcher.class).getId();
        result = restTemplate.postForEntity(new URI(url),
                new HttpEntity<>(new BackScratcher(-1L, "NAME", "DESC2", new String[]{"S", "L"}, 10.20d)), String.class);
        assertEquals(500, result.getStatusCodeValue());

        //clean DB
        restTemplate.delete(new URI(url + id));

        //assert is clean
        backScratchersResponse = restTemplate.getForEntity(new URI(url), BackScratcher[].class);
        assertTrue(backScratchersResponse.getBody().length == 0);
    }
}