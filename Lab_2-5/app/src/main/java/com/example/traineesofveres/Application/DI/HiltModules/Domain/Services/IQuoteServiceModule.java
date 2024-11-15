package com.example.traineesofveres.Application.DI.HiltModules.Domain.Services;


import com.example.traineesofveres.Domain.Connection.ConnectionManager.IConnectionManager;
import com.example.traineesofveres.Domain.DALInterfaces.IUnitOfWork;
import com.example.traineesofveres.Domain.Services.QuoteService.IQuoteService;
import com.example.traineesofveres.Domain.Services.QuoteService.QuoteService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class IQuoteServiceModule {

    @Provides
    @Singleton
    public static IQuoteService ProvideService(IUnitOfWork unitOfWork, IConnectionManager connectionManager){
        return new QuoteService(unitOfWork, connectionManager);
    }
}
