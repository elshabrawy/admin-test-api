package com.santechture.api.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) {
        // Generate a secure key for HS256
        byte[] keyBytes= Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        String base64Key=Base64.getEncoder().encodeToString(keyBytes);

        System.out.println("Secure JWT Secret: " + base64Key);
    }
}
