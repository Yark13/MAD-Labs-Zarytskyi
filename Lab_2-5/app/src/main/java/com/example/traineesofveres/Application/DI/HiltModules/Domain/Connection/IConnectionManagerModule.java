package com.example.traineesofveres.Application.DI.HiltModules.Domain.Connection;

import com.example.traineesofveres.Domain.Connection.ConnectionManager.ConnectionManager;
import com.example.traineesofveres.Domain.Connection.ConnectionManager.IConnectionManager;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class IConnectionManagerModule {
    @Binds
    @Singleton
    public  abstract IConnectionManager BindManager(ConnectionManager manager);
}
