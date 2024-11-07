package com.example.traineesofveres.Infrastructure;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.traineesofveres.DTO.Infrastructure.Quote;
import com.example.traineesofveres.DTO.Infrastructure.Trainee;

public class DatabaseHelper extends SQLiteOpenHelper {

    //private Context _context;
    private static final String Database_Name = "TraineeOfVeres.db";
    private static final int Database_Version = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTraineesTableQuery = "CREATE TABLE " + Trainee.GetDatabaseTableName() + "(" + Trainee.GetDatabaseTableParameters() + ")";
        String createQuotesTableQuery = "CREATE TABLE " + Quote.GetDatabaseTableName() + "(" + Quote.GetDatabaseTableParameters() + ")";

        sqLiteDatabase.execSQL(createTraineesTableQuery);
        sqLiteDatabase.execSQL(createQuotesTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + Trainee.GetDatabaseTableName());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + Quote.GetDatabaseTableName());

        onCreate(sqLiteDatabase);
    }
}
