package com.example.traineesofveres.Infrastructure.FireStore.Repository;

import com.example.traineesofveres.DTO.Infrastructure.Entity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireStoreRepository<T extends Entity>{

    protected final CollectionReference _collection;

    public FireStoreRepository(FirebaseFirestore firestore, String collectionPath){
        _collection = firestore.collection(collectionPath);
    }
}
