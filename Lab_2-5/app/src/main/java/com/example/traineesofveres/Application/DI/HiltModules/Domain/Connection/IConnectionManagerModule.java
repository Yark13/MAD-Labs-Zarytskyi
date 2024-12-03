package com.example.traineesofveres.Application.DI.HiltModules.Domain.Connection;

import android.content.Context;

import com.example.traineesofveres.Domain.Connection.ConnectionManager.ConnectionManager;
import com.example.traineesofveres.Domain.Connection.ConnectionManager.IConnectionManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class IConnectionManagerModule {
    @Provides
    @Singleton
    public static IConnectionManager provideManager(Context context){
        return new ConnectionManager(context);
    }
}
