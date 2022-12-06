package com.imcs.documentcollection.dto;

import java.security.SecureRandom;
import java.util.Base64;

public class CRMUtil {

    public static String generatePassword(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token); //base64 encoding
    }
}
