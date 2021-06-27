package com.itscout.app.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ClientControllerTest {
	
    @Test
    @DisplayName("Should validate index")
    public void valid_values_should_pass() {
        assertEquals("index", new ClientController().showIndex());
    }

    @Test
    @DisplayName("Should validate admin")
    public void invalid_values_should_fail() {
        assertEquals("admin", new ClientController().showAdmin());
    }
}
