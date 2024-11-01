package com.example.traineesofveres.Application.DI.HiltModules;

import com.example.traineesofveres.Domain.Security.IPasswordManager;
import com.example.traineesofveres.Domain.Security.PasswordManager;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class IPasswordManagerModule {

    @Binds
    @Singleton
    public  abstract IPasswordManager BindManager(PasswordManager manager);
}
