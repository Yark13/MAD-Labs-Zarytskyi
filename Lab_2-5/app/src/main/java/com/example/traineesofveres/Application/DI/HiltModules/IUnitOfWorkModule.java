package com.example.traineesofveres.Application.DI.HiltModules;

import com.example.traineesofveres.Domain.DALInterfaces.IUnitOfWork;
import com.example.traineesofveres.Infrastructure.SqLite.DatabaseHelper;
import com.example.traineesofveres.Infrastructure.SqLite.UnitOfWork.UnitOfWork;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
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
