package com.matinsa.backend;

import java.security.SecureRandom;
import java.util.Base64;

public class JWTSecretGenerator {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[64];
        random.nextBytes(bytes);
        String secret = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        System.out.println("JWT Secret: " + secret);
    }
}