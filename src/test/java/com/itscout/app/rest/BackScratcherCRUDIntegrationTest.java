package com.itscout.app.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.itscout.Application;
import com.itscout.domain.model.BackScratcher;

import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class BackScratcherCRUDIntegrationTest {

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

        // POST
        ResponseEntity<String> result = restTemplate.postForEntity(new URI(url),
                new HttpEntity<>(new BackScratcher(-1L, "NAME", "DESC", new String[]{"S", "L"}, 10.20d)), String.class);
        assertEquals(201, result.getStatusCodeValue());
        BackScratcher firstBackScratcher = objectMapper.readValue(result.getBody(), BackScratcher.class);
        assertNotNull(firstBackScratcher.getId());
        Assertions.assertEquals("NAME", firstBackScratcher.getName());

        // GET ALL
        backScratchersResponse = restTemplate.getForEntity(new URI(url), BackScratcher[].class);
        assertTrue(backScratchersResponse.getBody().length == 1);

        // PUT
        firstBackScratcher.setName("UPDATED_NAME");
        assertEquals(200, restTemplate.exchange(new URI(url + firstBackScratcher.getId()), HttpMethod.PUT,
                new HttpEntity<>(firstBackScratcher), BackScratcher.class).getStatusCodeValue());

        //GET BY ID
        ResponseEntity<BackScratcher> responseBackScratcher = restTemplate.getForEntity(new URI(url + firstBackScratcher.getId()),
                BackScratcher.class);
        Assertions.assertEquals("UPDATED_NAME", responseBackScratcher.getBody().getName());

        // DELETE
        restTemplate.delete(new URI(url + firstBackScratcher.getId()));
        backScratchersResponse = restTemplate.getForEntity(new URI(url), BackScratcher[].class);
        assertTrue(backScratchersResponse.getBody().length == 0);
    }
}