package com.example.traineesofveres.Infrastructure.UnitOfWork;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.traineesofveres.Domain.DALInterfaces.IRepository;
import com.example.traineesofveres.Domain.DALInterfaces.IUnitOfWork;
import com.example.traineesofveres.DTO.Infrastructure.Entity;
import com.example.traineesofveres.Infrastructure.DatabaseHelper;
import com.example.traineesofveres.Infrastructure.Repository.RepositoryFactory;

public class UnitOfWork implements IUnitOfWork {

    private final SQLiteOpenHelper _dbHelper;
    private final SQLiteDatabase _database;
    private final RepositoryFactory _repoFactory;

    public UnitOfWork(SQLiteOpenHelper dbHelper) {


        _dbHelper = dbHelper;
        _database = _dbHelper.getWritableDatabase();
        _repoFactory = new RepositoryFactory(_database);
    }


    @Override
    public <T extends Entity> IRepository<T> GetRepository(Class<T> classT) {
        return _repoFactory.GetRepository(classT);
    }

    @Override
    public void SaveChanges() {
        try {
            _database.beginTransaction();
            _database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            _database.endTransaction();
        }
    }

    @Override
    public void close() throws Exception {
        Disable();
    }

    private void Disable(){
        if (_database != null && _database.isOpen()) {
            _database.close();
        }
        _dbHelper.close();
    }

}
