package com.example.traineesofveres.Application.DI.HiltModules.Infrustructure;

import android.content.Context;

import com.example.traineesofveres.Infrastructure.DatabaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseHelperHelperModule {

    @Provides
    @Singleton
    public DatabaseHelper provideDatabaseHelper(Context context){
        return DatabaseHelper.getInstance(context);
    }

}
