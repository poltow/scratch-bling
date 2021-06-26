package com.itscout.domain.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class MessageTest {

    @Test
    @DisplayName("Should validate BackScratcher instance")
    public void create_instance() {
        Message message =  new Message("msg");
        assertEquals("msg", message.getMessage());
        message.setMessage("msg2");
        assertEquals("msg2", message.getMessage());
    }

    @Test
    @DisplayName("Should validate BackScratcher instance")
    public void equals_test() {
        Message message1 =  new Message("msg");
        Message message2 =  new Message("msg");
        assertTrue(message1.equals(message2));
        assertTrue(message1.hashCode()==message2.hashCode());
    }
}
