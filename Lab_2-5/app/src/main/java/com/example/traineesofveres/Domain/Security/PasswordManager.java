package com.example.traineesofveres.Domain.Security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.inject.Inject;

public class PasswordManager implements IPasswordManager{
    @Inject
    public PasswordManager() {
    }

    @Override
    public String HashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            String salt = "staticSaltValue123";

            String saltedPassword = salt + password;

            byte[] hash = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
