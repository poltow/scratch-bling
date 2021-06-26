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

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class BackScratcherGETIntegrationTest {

    public static final String NAME = "NAME_";
    public static final String DESC = "DESC_";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test() throws URISyntaxException {

        String url = "http://localhost:" + port + "/backscratchers/";
        ObjectMapper objectMapper = new ObjectMapper();

        int itemQty = 6;

        // BackScratcher table is empty
        ResponseEntity<BackScratcher[]> backScratchersResponse = restTemplate.getForEntity(new URI(url), BackScratcher[].class);
        assertTrue(backScratchersResponse.getBody().length == 0);

        // Post backScratchers
        for (int i = itemQty / 2; i < itemQty; i++) {
            restTemplate.postForEntity(new URI(url), new HttpEntity<>(createBackScratcher(i)), String.class);
        }
        for (int i = 1; i < itemQty / 2; i++) {
            restTemplate.postForEntity(new URI(url), new HttpEntity<>(createBackScratcher(i)), String.class);
        }
        backScratchersResponse = restTemplate.getForEntity(new URI(url), BackScratcher[].class);
        assertTrue(backScratchersResponse.getBody().length == itemQty - 1);
        BackScratcher[] backScratchers = backScratchersResponse.getBody();

        //Get by name
        String str = url + "name/" + NAME + "1";
        ResponseEntity<BackScratcher> backScratcher = restTemplate.getForEntity(new URI(str), BackScratcher.class);
        assertEquals(DESC + "1", backScratcher.getBody().getDescription());

        //clean DB
        for (int i = 0; i < backScratchers.length; i++) {
            restTemplate.delete(new URI(url + backScratchers[i].getId()));
        }

        //assert is clean
        backScratchersResponse = restTemplate.getForEntity(new URI(url), BackScratcher[].class);
        assertTrue(backScratchersResponse.getBody().length == 0);
    }

    private BackScratcher createBackScratcher(int i) {
        return new BackScratcher(-1L, NAME + i, DESC + i, new String[]{"S", "L"}, i * 11.2d);
    }
}