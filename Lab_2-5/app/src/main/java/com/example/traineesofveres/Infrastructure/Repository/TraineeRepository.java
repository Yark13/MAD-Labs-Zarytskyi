package com.example.traineesofveres.Infrastructure.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.traineesofveres.DTO.Domain.TraineeModel;
import com.example.traineesofveres.DTO.Infrastructure.Trainee;
import com.example.traineesofveres.Domain.DALInterfaces.IRepository;

import java.util.ArrayList;
import java.util.function.Predicate;

import kotlin.Pair;

public class TraineeRepository extends Repository<Trainee> implements IRepository<Trainee> {

    public static String GetDatabaseTableName() {
        return "trainees";
    }

    public static String GetDatabaseTableParameters() {
        return "id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "surname TEXT, " +
                "email TEXT, " +
                "password TEXT, " +
                "age INTEGER, " +
                "score INTEGER";
    }

    public TraineeRepository(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public ArrayList<Trainee> GetAll() {
        ArrayList<Trainee> trainees = new ArrayList<>();
        Cursor cursor = _database.query(GetDatabaseTableName(), null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            trainees.add(buildTraineeFromCursor(cursor));
        }
        cursor.close();
        return trainees;
    }

    @Override
    public ArrayList<Trainee> GetAll(Predicate<Trainee> filter) {
        ArrayList<Trainee> allTrainees = GetAll();
        ArrayList<Trainee> filteredTrainees = new ArrayList<>();
        for (Trainee trainee : allTrainees) {
            if (filter.test(trainee)) {
                filteredTrainees.add(trainee);
            }
        }
        return filteredTrainees;
    }

    @Override
    public ArrayList<Trainee> GetAll(int skip, int take) {
        ArrayList<Trainee> trainees = new ArrayList<>();
        String query = "SELECT * FROM " + GetDatabaseTableName() + " LIMIT ? OFFSET ?";
        Cursor cursor = _database.rawQuery(query, new String[]{String.valueOf(take), String.valueOf(skip)});
        while (cursor.moveToNext()) {
            trainees.add(buildTraineeFromCursor(cursor));
        }
        cursor.close();
        return trainees;
    }

    @Override
    public ArrayList<Trainee> GetAll(Predicate<Trainee> filter, int skip, int take) {
        ArrayList<Trainee> allTrainees = GetAll();
        ArrayList<Trainee> filteredTrainees = new ArrayList<>();
        for (int i = skip; i < allTrainees.size() && filteredTrainees.size() < take; i++) {
            Trainee trainee = allTrainees.get(i);
            if (filter.test(trainee)) {
                filteredTrainees.add(trainee);
            }
        }
        return filteredTrainees;
    }

    @Override
    public ArrayList<Pair<Trainee, Integer>> GetTopWithRank(int topCount, int traineeId) {
        ArrayList<Pair<Trainee, Integer>> resultList = new ArrayList<>();

        String queryGetTop = "SELECT id, name, surname, email, age, score, " +
                "(SELECT COUNT(*) + 1 FROM " + GetDatabaseTableName() + " WHERE score > t.score) AS rank " +
                "FROM " + GetDatabaseTableName() + " t " +
                "ORDER BY score DESC " +
                "LIMIT ?;";

        String queryGetRankTraineeWithId = "SELECT id, name, surname, email, age, score, " +
                "(SELECT COUNT(*) + 1 FROM " + GetDatabaseTableName() + " WHERE score > (SELECT score FROM " + GetDatabaseTableName() + " WHERE id = ?)) AS rank " +
                "FROM " + GetDatabaseTableName() + " WHERE id = ?;";

        Cursor cursor = null;
        Cursor cursor1 = null;

        try {
            cursor = _database.rawQuery(queryGetTop, new String[]{String.valueOf(topCount)});

            if (cursor.moveToFirst()) {
                do {
                    resultList.add(buildPairTraineeIntegerFromCursor(cursor));
                } while (cursor.moveToNext());
            }

            cursor1 = _database.rawQuery(queryGetRankTraineeWithId, new String[]{String.valueOf(traineeId), String.valueOf(traineeId)});
            cursor1.moveToFirst();
            
            resultList.add(buildPairTraineeIntegerFromCursor(cursor1));
        } finally {
            if (cursor != null) {
                cursor.close();
            }

            if (cursor1 != null) {
                cursor1.close();
            }
        }

        return resultList;
    }


    @Override
    public Trainee Find(int id) {
        Cursor cursor = _database.query(GetDatabaseTableName(), null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            Trainee trainee = buildTraineeFromCursor(cursor);
            cursor.close();
            return trainee;
        }
        cursor.close();
        return null;
    }

    @Override
    public Trainee Add(Trainee trainee) {
        ContentValues values = getContentValuesFromTrainee(trainee);
        _database.insert(GetDatabaseTableName(), null, values);
        return trainee;
    }

    @Override
    public Trainee Update(Trainee entity) {
        ContentValues values = getContentValuesFromTrainee(entity);
        _database.update(GetDatabaseTableName(), values, "id = ?", new String[]{String.valueOf(entity.Id)});
        return entity;
    }

    @Override
    public void Delete(int id) {
        _database.delete(GetDatabaseTableName(), "id = ?", new String[]{String.valueOf(id)});
    }

    private Trainee buildTraineeFromCursor(Cursor cursor) {
        Trainee trainee = new Trainee();
        trainee.Id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        trainee.Name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        trainee.Surname = cursor.getString(cursor.getColumnIndexOrThrow("surname"));
        trainee.Email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
        trainee.Password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
        trainee.Age = cursor.getInt(cursor.getColumnIndexOrThrow("age"));
        trainee.Score = cursor.getInt(cursor.getColumnIndexOrThrow("score"));
        return trainee;
    }

    private Pair<Trainee, Integer> buildPairTraineeIntegerFromCursor(Cursor cursor) {
        Trainee trainee = new Trainee();
        trainee.Id = cursor.getInt(0);
        trainee.Name = cursor.getString(1);
        trainee.Surname = cursor.getString(2);
        trainee.Email = cursor.getString(3);
        trainee.Age = cursor.getInt(4);
        trainee.Score = cursor.getInt(5);
        int rank = cursor.getInt(6);
        return new Pair<>(trainee, rank);
    }

    private ContentValues getContentValuesFromTrainee(Trainee trainee) {
        ContentValues values = new ContentValues();
        values.put("name", trainee.Name);
        values.put("surname", trainee.Surname);
        values.put("email", trainee.Email);
        values.put("password", trainee.Password);
        values.put("age", trainee.Age);
        values.put("score", trainee.Score);
        return values;
    }
}
