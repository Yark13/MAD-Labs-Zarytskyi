package com.example.traineesofveres.Infrastructure.Repository;

import android.database.sqlite.SQLiteDatabase;

import com.example.traineesofveres.DTO.Domain.TraineeModel;
import com.example.traineesofveres.DTO.Infrastructure.Quote;
import com.example.traineesofveres.Domain.DALInterfaces.IRepository;

import java.util.ArrayList;
import java.util.function.Predicate;

public class QuoteRepository extends Repository<Quote> implements IRepository<Quote> {
    public QuoteRepository(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public ArrayList<Quote> GetAll() {
        return null;
    }

    @Override
    public ArrayList<Quote> GetAll(Predicate<Quote> filter) {
        return null;
    }

    @Override
    public ArrayList<Quote> GetAll(int skip, int take) {
        return null;
    }

    @Override
    public ArrayList<Quote> GetAll(Predicate<Quote> filter, int skip, int take) {
        return null;
    }

    @Override
    public ArrayList<TraineeModel> GetTopWithRank(int topCount, int traineeId) {
        return null;
    }

    @Override
    public Quote Find(int id) {
        return null;
    }

    @Override
    public Quote Add(Quote entity) {
        return null;
    }

    @Override
    public Quote Update(Quote entity) {
        return null;
    }

    @Override
    public void Delete() {

    }
}
