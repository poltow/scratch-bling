package com.itscout.domain.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static com.itscout.domain.model.Size.isValid;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class SizeTest {

    @Test
    @DisplayName("Should validate S, M, L, XL")
    public void valid_values_should_pass() {
        assertTrue(isValid("S"));
        assertTrue(isValid("M"));
        assertTrue(isValid("L"));
        assertTrue(isValid("XL"));
    }

    @Test
    @DisplayName("Should not validate XXX")
    public void invalid_values_should_fail() {
        assertFalse(isValid("XXX"));
        assertFalse(isValid("XL "));
        assertFalse(isValid("s"));
        assertFalse(isValid("J"));
        assertFalse(isValid(""));
        assertFalse(isValid(null));
    }
}
