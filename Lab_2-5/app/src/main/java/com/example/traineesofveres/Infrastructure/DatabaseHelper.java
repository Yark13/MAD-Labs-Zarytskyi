package com.example.traineesofveres.Infrastructure;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.traineesofveres.DTO.Infrastructure.Quote;
import com.example.traineesofveres.DTO.Infrastructure.Trainee;
import com.example.traineesofveres.Infrastructure.Repository.QuoteRepository;
import com.example.traineesofveres.Infrastructure.Repository.TraineeRepository;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper _instance;
    private static final String Database_Name = "TraineeOfVeres.db";
    private static final int Database_Version = 1;

    public static synchronized DatabaseHelper getInstance(Context context){
        if(_instance == null){
            _instance = new DatabaseHelper(context.getApplicationContext());
        }

        return _instance;
    }

    private DatabaseHelper(@Nullable Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTraineesTableQuery = "CREATE TABLE " + TraineeRepository.GetDatabaseTableName() + " (" + TraineeRepository.GetDatabaseTableParameters() + ")";
        String createQuotesTableQuery = "CREATE TABLE " + QuoteRepository.GetDatabaseTableName() + " (" + QuoteRepository.GetDatabaseTableParameters() + ")";

        sqLiteDatabase.execSQL(createTraineesTableQuery);
        sqLiteDatabase.execSQL(createQuotesTableQuery);

        insertTestData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TraineeRepository.GetDatabaseTableName());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + QuoteRepository.GetDatabaseTableName());

        onCreate(sqLiteDatabase);
    }

    private void insertTestData(SQLiteDatabase db) {
        String[] traineeInserts = new String[]{
                "INSERT INTO trainees (name, surname, email, password, age, score) VALUES ('John', 'Doe', 'john.doe@example.com', 'password123', 25, 85);",
                "INSERT INTO trainees (name, surname, email, password, age, score) VALUES ('Jane', 'Smith', 'jane.smith@example.com', 'password456', 22, 92);",
                "INSERT INTO trainees (name, surname, email, password, age, score) VALUES ('Alice', 'Johnson', 'alice.j@example.com', 'password789', 30, 78);",
                "INSERT INTO trainees (name, surname, email, password, age, score) VALUES ('Bob', 'Brown', 'bob.brown@example.com', 'password321', 28, 88);",
                "INSERT INTO trainees (name, surname, email, password, age, score) VALUES ('Charlie', 'Davis', 'charlie.d@example.com', 'password654', 23, 95);"
        };

        db.beginTransaction();
        try {
            for (String insert : traineeInserts) {
                db.execSQL(insert);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
