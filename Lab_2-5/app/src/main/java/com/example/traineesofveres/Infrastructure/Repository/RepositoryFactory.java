package com.example.traineesofveres.Infrastructure.Repository;

import android.database.sqlite.SQLiteDatabase;

import com.example.traineesofveres.DTO.Infrastructure.Entity;
import com.example.traineesofveres.DTO.Infrastructure.Quote;
import com.example.traineesofveres.DTO.Infrastructure.Trainee;
import com.example.traineesofveres.Domain.DALInterfaces.IRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RepositoryFactory {
    private final SQLiteDatabase _database;
    Map<Class<? extends Entity>, Supplier<IRepository<? extends Entity>>> _supplier = new HashMap<>();

    public RepositoryFactory(SQLiteDatabase database) {
        this._database = database;

        PrepareSupplier();
    }

    public <T extends Entity> IRepository<T> GetRepository(Class<T> classT){

        IRepository<T> repository = (IRepository<T>) _supplier.get(classT).get();;
        return repository;
    }

    private void PrepareSupplier(){
        _supplier.put(Trainee.class, () -> new TraineeRepository(_database));
        _supplier.put(Quote.class, () -> new QuoteRepository(_database));
    }
}
