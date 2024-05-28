package com.marvel.service;

import com.marvel.config.MarvelConfig;
import com.marvel.dto.MarvelResponse;
import com.marvel.exception.MarvelException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class MarvelServiceLibImplTest {

    @Mock
    private RestTemplate restTemplateMock;

    @Mock
    private MarvelConfig marvelConfigMock;

    @InjectMocks
    private MarvelServiceImpl marvelServiceLib;

    private static String responseAllCharactersJson = null;
    private static String responseCharactrByIdJson = null;

    private String urlStringGetAllCharacts = "/characters?ts=123&apikey=123&hash=f5bb0c8de146c67b44babbf4e6584cc0";
    private String urlStringGetAllCharactsById = "/characters/1011334?ts=123&apikey=123&hash=f5bb0c8de146c67b44babbf4e6584cc0";

    @BeforeAll
    static void beforeAll() throws IOException {

        //For All Characters
        File fileOne = ResourceUtils.getFile("classpath:response_marvel_characters.json");
        byte[] jsonBytesOne = Files.readAllBytes(Paths.get(fileOne.getPath()));
        responseAllCharactersJson = new String(jsonBytesOne);

        //For Character By ID
        File fileTwo = ResourceUtils.getFile("classpath:response_marvel_character_by_id.json");
        byte[] jsonBytes = Files.readAllBytes(Paths.get(fileTwo.getPath()));
        responseCharactrByIdJson = new String(jsonBytes);
    }

    @Test
    void test_get_All_Characters_successfully_ok() throws MarvelException {
        Mockito.when(marvelConfigMock.getUrlApiMarvel()).thenReturn("/");
        Mockito.when(marvelConfigMock.getPublicKey()).thenReturn("123");
        Mockito.when(marvelConfigMock.getPrivateKey()).thenReturn("123");
        Mockito.when(marvelConfigMock.getTimestamp()).thenReturn("123");

        Mockito.when(restTemplateMock.exchange(urlStringGetAllCharacts, HttpMethod.GET, null, String.class))
                .thenReturn(new ResponseEntity<>(responseAllCharactersJson, HttpStatus.OK));

        marvelServiceLib.setRestTemplate(restTemplateMock);

        MarvelResponse allCharaters = marvelServiceLib.getAllCharaters();

        System.out.println(allCharaters.toString());

        assertNotNull(allCharaters);
    }

    @Test
    void test_get_All_Characters_bad_request() {
        Mockito.when(marvelConfigMock.getUrlApiMarvel()).thenReturn("/");
        Mockito.when(marvelConfigMock.getPublicKey()).thenReturn("123");
        Mockito.when(marvelConfigMock.getPrivateKey()).thenReturn("123");
        Mockito.when(marvelConfigMock.getTimestamp()).thenReturn("123");

        Mockito.when(restTemplateMock.exchange(urlStringGetAllCharacts, HttpMethod.GET, null, String.class))
                .thenReturn(new ResponseEntity<>(responseAllCharactersJson, HttpStatus.BAD_REQUEST));

        marvelServiceLib.setRestTemplate(restTemplateMock);

        MarvelException myException = assertThrows(
                MarvelException.class,
                () -> marvelServiceLib.getAllCharaters(),
                "Error to get the information from Api Marvel, Error"
        );

        assertTrue(myException.getMessage().contains("Api Marvel"));
    }

    @Test
    void test_get_Charater_By_Id_successfully() throws MarvelException {
        Mockito.when(marvelConfigMock.getUrlApiMarvel()).thenReturn("/");
        Mockito.when(marvelConfigMock.getPublicKey()).thenReturn("123");
        Mockito.when(marvelConfigMock.getPrivateKey()).thenReturn("123");
        Mockito.when(marvelConfigMock.getTimestamp()).thenReturn("123");

        Mockito.when(restTemplateMock.exchange(urlStringGetAllCharactsById, HttpMethod.GET, null, String.class))
                .thenReturn(new ResponseEntity<>(responseCharactrByIdJson, HttpStatus.OK));

        marvelServiceLib.setRestTemplate(restTemplateMock);

        MarvelResponse characteById = marvelServiceLib.getCharaterById("1011334");

        System.out.println(characteById.toString());
        assertNotNull(characteById);
    }

    @Test
    void test_get_Charater_By_Id_bad_request() {
        Mockito.when(marvelConfigMock.getUrlApiMarvel()).thenReturn("/");
        Mockito.when(marvelConfigMock.getPublicKey()).thenReturn("123");
        Mockito.when(marvelConfigMock.getPrivateKey()).thenReturn("123");
        Mockito.when(marvelConfigMock.getTimestamp()).thenReturn("123");

        Mockito.when(restTemplateMock.exchange(urlStringGetAllCharactsById, HttpMethod.GET, null, String.class))
                .thenReturn(new ResponseEntity<>(responseCharactrByIdJson, HttpStatus.BAD_REQUEST));

        marvelServiceLib.setRestTemplate(restTemplateMock);

        MarvelException myException = assertThrows(
                MarvelException.class,
                () -> marvelServiceLib.getCharaterById("1011334"),
                "Error to get the information from Api Marvel, Error"
        );

        assertTrue(myException.getMessage().contains("Api Marvel"));
    }
}