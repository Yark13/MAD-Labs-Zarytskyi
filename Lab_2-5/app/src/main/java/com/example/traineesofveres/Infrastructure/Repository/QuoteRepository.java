package com.example.traineesofveres.Infrastructure.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.traineesofveres.DTO.Infrastructure.Quote;
import com.example.traineesofveres.Domain.DALInterfaces.IRepository;

import java.util.ArrayList;
import java.util.function.Predicate;

import kotlin.Pair;

public class QuoteRepository extends Repository<Quote> implements IRepository<Quote> {

    public static String GetDatabaseTableName() {
        return "quotes";
    }

    public static String GetDatabaseTableParameters() {
        return "id INTEGER PRIMARY KEY," +
                "text TEXT, " +
                "traineePublisherId INTEGER, " +
                "dateOfPublication TEXT";
    }

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
        ArrayList<Quote> quotes = new ArrayList<>();
        String query = "SELECT * FROM " + GetDatabaseTableName() + " LIMIT ? OFFSET ?";
        Cursor cursor = _database.rawQuery(query, new String[]{String.valueOf(take), String.valueOf(skip)});
        while (cursor.moveToNext()) {
            quotes.add(buildQuoteFromCursor(cursor));
        }
        cursor.close();
        return quotes;
    }

    @Override
    public ArrayList<Pair<Quote, Integer>> GetTopWithRank(int topCount, int traineeId) {
        return null;
    }

    @Override
    public Quote Find(int id) {
        return null;
    }

    @Override
    public Quote Add(Quote entity) {
        ContentValues values = getContentValuesFromQuote(entity);
        _database.insert(GetDatabaseTableName(), null, values);
        return entity;
    }

    @Override
    public Quote Update(Quote entity) {
        return null;
    }

    private Quote buildQuoteFromCursor(Cursor cursor) {
        Quote quote = new Quote();
        quote.Id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        quote.Text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
        quote.DateOfPublication = cursor.getString(cursor.getColumnIndexOrThrow("dateOfPublication"));
        quote.TraineePublisherId = cursor.getInt(cursor.getColumnIndexOrThrow("traineePublisherId"));
        return quote;
    }

    private ContentValues getContentValuesFromQuote(Quote quote) {
        ContentValues values = new ContentValues();
        values.put("text", quote.Text);
        values.put("dateOfPublication", quote.DateOfPublication);
        values.put("traineePublisherId", quote.TraineePublisherId);
        return values;
    }
}
