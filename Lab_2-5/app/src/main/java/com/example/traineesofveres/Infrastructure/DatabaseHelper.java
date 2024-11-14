package com.example.traineesofveres.Infrastructure;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.traineesofveres.Domain.Security.IPasswordManager;
import com.example.traineesofveres.Domain.Security.PasswordManager;
import com.example.traineesofveres.Infrastructure.Repository.QuoteRepository;
import com.example.traineesofveres.Infrastructure.Repository.TraineeRepository;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper _instance;
    private static final String Database_Name = "TraineeOfVeres.db";
    private static final int Database_Version = 1;

    private final IPasswordManager _passwordManager;

    public static synchronized DatabaseHelper getInstance(Context context){
        if(_instance == null){
            _instance = new DatabaseHelper(context.getApplicationContext());
        }

        return _instance;
    }

    private DatabaseHelper(@Nullable Context context) {
        super(context, Database_Name, null, Database_Version);

        _passwordManager = new PasswordManager();
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
                "INSERT INTO trainees (name, surname, email, password, age, score) VALUES ('John', 'Doe', 'john.doe@example.com', '" + _passwordManager.HashPassword("password123") + "', 25, 85);",
                "INSERT INTO trainees (name, surname, email, password, age, score) VALUES ('Jane', 'Smith', 'jane.smith@example.com', '" + _passwordManager.HashPassword("password456") + "', 22, 92);",
                "INSERT INTO trainees (name, surname, email, password, age, score) VALUES ('Alice', 'Johnson', 'alice.j@example.com', '" + _passwordManager.HashPassword("password789") + "', 30, 78);",
                "INSERT INTO trainees (name, surname, email, password, age, score) VALUES ('Bob', 'Brown', 'bob.brown@example.com', '" + _passwordManager.HashPassword("password321") + "', 28, 88);",
                "INSERT INTO trainees (name, surname, email, password, age, score) VALUES ('Charlie', 'Davis', 'charlie.d@example.com', '" + _passwordManager.HashPassword("password654") + "', 23, 95);"
        };

        String[] quotesInserts = new String[]{
                "INSERT INTO quotes (text, traineePublisherId, dateOfPublication) VALUES ('Як виявилось AI, то не панацея', '3', '25.10.2024');",
                "INSERT INTO quotes (text, traineePublisherId, dateOfPublication) VALUES ('Я вас зараз додам в .gitignore. Вам це не сподобається', '2', '23.10.2024');",
                "INSERT INTO quotes (text, traineePublisherId, dateOfPublication) VALUES ('Я у мами інженер', '2', '23.10.2024');",
                "INSERT INTO quotes (text, traineePublisherId, dateOfPublication) VALUES ('Я на вас гавкаю не тому, що я квадробер, просто після Вашого коду, чомусь хочеться гавкати;', '1', '22.10.2024')",
                "INSERT INTO quotes (text, traineePublisherId, dateOfPublication) VALUES ('Мілості прошу к нашому шалашу;', '5', '22.10.2024')",
                "INSERT INTO quotes (text, traineePublisherId, dateOfPublication) VALUES ('Якщо закриваються двері, то відкриваються інші. Якщо ви думаєте, шо це з психології, то ні це про радянське машинобудування;', '4', '02.09.2024')"

        };

        db.beginTransaction();
        try {
            for (String insert : traineeInserts) {
                db.execSQL(insert);
            }

            for (String insert : quotesInserts){
                db.execSQL(insert);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
