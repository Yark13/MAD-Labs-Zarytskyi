package com.example.traineesofveres.Application.DI.HiltModules;

import com.example.traineesofveres.Domain.DALInterfaces.IUnitOfWork;
import com.example.traineesofveres.Infrastructure.FireStore.UnitOfWork.FirestoreUnitOfWork;
import com.google.firebase.firestore.FirebaseFirestore;

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
    public static IUnitOfWork provideUnitOfWork(FirebaseFirestore firestore){
        return new FirestoreUnitOfWork(firestore);
    }
}
