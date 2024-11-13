package com.example.traineesofveres.Application.DI.HiltModules.Domain.Services;

import com.example.traineesofveres.Domain.DALInterfaces.IUnitOfWork;
import com.example.traineesofveres.Domain.Security.IPasswordManager;
import com.example.traineesofveres.Domain.Services.TraineeService.ITraineeService;
import com.example.traineesofveres.Domain.Services.TraineeService.TraineeService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ITraineeServiceModule {

    @Provides
    @Singleton
    public static ITraineeService provideService(IUnitOfWork unitOfWork, IPasswordManager passwordManager){
        return new TraineeService(unitOfWork, passwordManager);
    }
}
