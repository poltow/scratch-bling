package com.itscout.domain.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

import static com.itscout.domain.model.Size.isValid;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class BackScratcherTest {

    @Test
    @DisplayName("Should validate BackScratcher instance")
    public void create_instance() {
        BackScratcher backScratcher =  new BackScratcher(-1L, "NAME", "DESC", new String[]{"S", "L"}, 10.20d);
        assertTrue(-1L==backScratcher.getId());
        assertEquals("NAME", backScratcher.getName());
        assertEquals("DESC", backScratcher.getDescription());
        List<@NotNull String> sizes = Arrays.asList(backScratcher.getSizes());
        assertTrue(sizes.contains("S"));
        assertTrue(sizes.contains("L"));
        assertEquals("NAME", backScratcher.getName());
        assertTrue(10.20d==backScratcher.getPrice());
    }

    @Test
    @DisplayName("Should validate BackScratcher instance")
    public void equals_test() {
        BackScratcher backScratcher1 =  new BackScratcher(-1L, "NAME", "DESC", new String[]{"S", "L"}, 10.20d);
        BackScratcher backScratcher2 =  new BackScratcher(-1L, "NAME", "DESC", new String[]{"S", "L"}, 10.20d);
        assertTrue(backScratcher1.equals(backScratcher2));
        assertTrue(backScratcher1.hashCode()==backScratcher2.hashCode());
    }
}
