package com.example.traineesofveres.Application.DI.HiltModules;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.traineesofveres.Domain.DALInterfaces.IUnitOfWork;
import com.example.traineesofveres.Infrastructure.DatabaseHelper;
import com.example.traineesofveres.Infrastructure.UnitOfWork.UnitOfWork;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class IUnitOfWorkModule {

    @Provides
    @Singleton
    public static IUnitOfWork provideUnitOfWork(DatabaseHelper dbHelper){
        return new UnitOfWork(dbHelper);
    }
}
