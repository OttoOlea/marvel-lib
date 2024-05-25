package com.marvel.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.marvel.config.MarvelConfig;
import com.marvel.dto.*;
import com.marvel.exception.MarvelException;
import com.marvel.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MarvelServiceImpl implements MarvelService {

    @Autowired
    private MarvelConfig marvelConfig;

    private RestTemplate restTemplate;

    private MarvelResponse response = null;

    @Override
    public MarvelResponse getAllCharaters() throws MarvelException {
        try {
            String urlApiMarvel = getURLMarvelApi("characters?");

            ResponseEntity<String> responseApiMarvel = this.restTemplate.exchange(urlApiMarvel, HttpMethod.GET, null, String.class);

            if (responseApiMarvel.getStatusCode() == HttpStatusCode.valueOf(200)) {
                response = convertResponseToDto(responseApiMarvel.getBody());

            } else {
                throw new MarvelException("Error to get the information from Api Marvel, Error: "
                        + responseApiMarvel.getStatusCode() + " " + responseApiMarvel.getBody());
            }

        } catch (MarvelException mar) {
            throw mar;

        } catch (Exception err) {
            throw new MarvelException("Error to getting the information, Error: " + err.getMessage());
        }

        return response;
    }

    @Override
    public MarvelResponse getCharaterById(String charaterId) throws MarvelException {
        try {

            String urlApiMarvel = getURLMarvelApi("characters/" + charaterId + "?");

            ResponseEntity<String> responseApiMarvel = this.restTemplate.exchange(urlApiMarvel, HttpMethod.GET, null, String.class);

            if (responseApiMarvel.getStatusCode() == HttpStatusCode.valueOf(200)) {
                response = convertResponseToDto(responseApiMarvel.getBody());

            } else {
                throw new MarvelException("Error to get the information from Api Marvel, Error: "
                        + responseApiMarvel.getStatusCode() + " " + responseApiMarvel.getBody());
            }

        } catch (Exception err) {
            throw new MarvelException("Error to getting the information, Error: " + err.getMessage());
        }
        return response;

    }

    private MarvelResponse convertResponseToDto(String responseBody) throws MarvelException {
        JsonObject jsonObject;

        MarvelResponse marvelResponse = null;
        CharacterDto characterDto;

        try {
            jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonObject dataJson = jsonObject.get("data").getAsJsonObject();

            marvelResponse = new MarvelResponse();
            marvelResponse.setTotal(dataJson.get("total").getAsInt());
            marvelResponse.setCount(dataJson.get("count").getAsInt());
            marvelResponse.setLimit(dataJson.get("limit").getAsInt());

            JsonArray resultsArray = dataJson.get("results").getAsJsonArray();

            for (JsonElement itemResult : resultsArray) {
                JsonObject itemObject = itemResult.getAsJsonObject();

                characterDto = new CharacterDto();
                characterDto.setId(itemObject.get("id").getAsInt());
                characterDto.setName(itemObject.get("name").getAsString());
                characterDto.setDescription(itemObject.get("description").getAsString());

                JsonObject jsonItemImagen = itemObject.get("thumbnail").getAsJsonObject();
                if (jsonItemImagen != null) {
                    ImageDto imageDto = new ImageDto();
                    imageDto.setPath(jsonItemImagen.get("path").getAsString());
                    imageDto.setExtension(jsonItemImagen.get("extension").getAsString());

                    characterDto.setImage(imageDto);
                }

                JsonObject comicsJson = itemObject.get("comics").getAsJsonObject();
                JsonArray arrayComics = comicsJson.get("items").getAsJsonArray();

                for (JsonElement itemComic : arrayComics) {
                    JsonObject comicJson = itemComic.getAsJsonObject();

                    ComicDto comicDto = new ComicDto();
                    comicDto.setName(comicJson.get("name").getAsString());
                    comicDto.setResourceURI(comicJson.get("resourceURI").getAsString());

                    characterDto.getListComics().add(comicDto);
                }

                JsonObject seriesJson = itemObject.get("series").getAsJsonObject();
                JsonArray arraySeries = seriesJson.get("items").getAsJsonArray();

                for (JsonElement itemSerie : arraySeries) {
                    JsonObject serieJson = itemSerie.getAsJsonObject();

                    SerieDTo serieDTo = new SerieDTo();
                    serieDTo.setName(serieJson.get("name").getAsString());
                    serieDTo.setResourceURI(serieJson.get("resourceURI").getAsString());

                    characterDto.getListSeries().add(serieDTo);
                }

                marvelResponse.getResultList().add(characterDto);
            }

        } catch (Exception err) {
            throw new MarvelException("Error to convert json to MarvelDto, Error:" + err.getMessage());
        }
        return marvelResponse;
    }

    private String getURLMarvelApi(String paramUrl) throws MarvelException {
        StringBuilder newURLMarvel = new StringBuilder(marvelConfig.getUrlApiMarvel());
        newURLMarvel.append(paramUrl);
        newURLMarvel.append("ts=").append(marvelConfig.getTimestamp());
        newURLMarvel.append("&");
        newURLMarvel.append("apikey=").append(marvelConfig.getPublicKey());
        newURLMarvel.append("&");
        newURLMarvel.append("hash=").append(generateHash());
        return newURLMarvel.toString();
    }

    private String generateHash() throws MarvelException {
        StringBuilder formatString = new StringBuilder();
        formatString.append(marvelConfig.getTimestamp());
        formatString.append(marvelConfig.getPrivateKey());
        formatString.append(marvelConfig.getPublicKey());
        return Util.getHashMD5(formatString.toString());
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
