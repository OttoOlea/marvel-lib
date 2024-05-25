package com.marvel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MarvelConfig {

    @Value("${marvel.config.key.public}")
    private String publicKey;

    @Value("${marvel.config.key.private}")
    private String privateKey;

    @Value("${marvel.config.timestamp}")
    private String timestamp;

    @Value("${marvel.path-base.api}")
    private String urlApiMarvel;

    public MarvelConfig() {
    }

    public MarvelConfig(String publicKey, String privateKey, String timestamp, String urlApi) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.timestamp = timestamp;
        this.urlApiMarvel = urlApi;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrlApiMarvel() {
        return urlApiMarvel;
    }

    public void setUrlApiMarvel(String urlApiMarvel) {
        this.urlApiMarvel = urlApiMarvel;
    }
}
