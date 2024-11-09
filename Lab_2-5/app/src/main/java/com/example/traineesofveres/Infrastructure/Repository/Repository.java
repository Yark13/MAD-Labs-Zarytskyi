package com.example.traineesofveres.Infrastructure.Repository;

import android.database.sqlite.SQLiteDatabase;

import com.example.traineesofveres.DTO.Infrastructure.Entity;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Repository<T extends Entity> {

    protected final SQLiteDatabase _database;

    public Repository(SQLiteDatabase database) {
        this._database = database;
    }
}
