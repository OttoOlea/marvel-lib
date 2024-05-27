package com.marvel.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class MarvelLibConfigTest {

    @Autowired
    private MarvelConfig marvelLibConfig;

    @Test
    void test_properties_are_equals() {
        assertEquals("4eca178ca719e6d", marvelLibConfig.getPrivateKey());
        assertEquals("49e6db64511c20ada1a777", marvelLibConfig.getPublicKey());
    }
}