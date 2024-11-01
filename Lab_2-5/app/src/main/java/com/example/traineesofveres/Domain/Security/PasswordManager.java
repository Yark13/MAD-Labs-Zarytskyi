package com.example.traineesofveres.Domain.Security;

import javax.inject.Inject;

public class PasswordManager implements IPasswordManager{
    @Inject
    public PasswordManager() {
    }

    @Override
    public String HashPassword(String password) {
        return "";
    }
}
