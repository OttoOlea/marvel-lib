package com.marvel.util;

import com.marvel.exception.MarvelException;

import java.security.MessageDigest;

public class Util {

    public static String getHashMD5(String value) throws MarvelException {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] hash = md5.digest(value.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();

        } catch (Exception err) {
            throw new MarvelException("Error to create the Hash MD5, Error" + err.getMessage());
        }
    }

}
