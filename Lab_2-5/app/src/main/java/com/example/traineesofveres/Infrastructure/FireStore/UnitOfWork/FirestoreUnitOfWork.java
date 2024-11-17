package com.example.traineesofveres.Infrastructure.FireStore.UnitOfWork;

import com.example.traineesofveres.DTO.Infrastructure.Entity;
import com.example.traineesofveres.Domain.DALInterfaces.IRepository;
import com.example.traineesofveres.Domain.DALInterfaces.IUnitOfWork;
import com.example.traineesofveres.Infrastructure.FireStore.Repository.FireStoreRepositoryFactory;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class FirestoreUnitOfWork implements IUnitOfWork {
    private final FirebaseFirestore _fireStore;
    private final FireStoreRepositoryFactory _repositoryFactory;

    public FirestoreUnitOfWork(FirebaseFirestore firestore) {
        _fireStore = Objects.requireNonNull(firestore);
        _repositoryFactory = new FireStoreRepositoryFactory(_fireStore);
    }

    @Override
    public <T extends Entity> IRepository<T> GetRepository(Class<T> classT) {
        return _repositoryFactory.GetRepository(classT);
    }

    @Override
    public void SaveChanges() {

    }

    @Override
    public void close() throws Exception {

    }
}
