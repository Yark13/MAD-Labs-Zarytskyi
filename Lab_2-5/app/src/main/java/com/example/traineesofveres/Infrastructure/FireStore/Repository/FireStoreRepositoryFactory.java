package com.example.traineesofveres.Infrastructure.FireStore.Repository;

import com.example.traineesofveres.DTO.Infrastructure.Entity;
import com.example.traineesofveres.DTO.Infrastructure.Quote;
import com.example.traineesofveres.DTO.Infrastructure.Trainee;
import com.example.traineesofveres.Domain.DALInterfaces.IRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FireStoreRepositoryFactory {
    FirebaseFirestore _firestore;
    Map<Class<? extends Entity>, Supplier<IRepository<? extends Entity>>> _supplier = new HashMap<>();

    public FireStoreRepositoryFactory(FirebaseFirestore firestore) {
        _firestore = firestore;
        PrepareSupplier();
    }

    public <T extends Entity> IRepository<T> GetRepository(Class<T> classT){

        IRepository<T> repository = (IRepository<T>) _supplier.get(classT).get();;
        return repository;
    }

    private void PrepareSupplier(){
        _supplier.put(Trainee.class, () -> new FireStoreTraineeRepository(_firestore));
        _supplier.put(Quote.class, () -> new FireStoreQuoteRepository(_firestore));
    }
}
