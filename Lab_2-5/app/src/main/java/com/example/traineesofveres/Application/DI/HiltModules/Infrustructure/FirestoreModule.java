package com.example.traineesofveres.Application.DI.HiltModules.Infrustructure;

import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class FirestoreModule {
    @Provides
    @Singleton
    public FirebaseFirestore provideFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }
}
