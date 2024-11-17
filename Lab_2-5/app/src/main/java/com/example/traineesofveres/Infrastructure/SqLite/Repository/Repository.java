package com.example.traineesofveres.Infrastructure.SqLite.Repository;

import android.database.sqlite.SQLiteDatabase;

import com.example.traineesofveres.DTO.Infrastructure.Entity;

public class Repository<T extends Entity> {

    protected final SQLiteDatabase _database;

    public Repository(SQLiteDatabase database) {
        this._database = database;
    }
}
