package com.lucasp243.androidtp4_todolist.model;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lucasp243.androidtp4_todolist.ToDoListApplication;

public final class ToDoListApplicationDatabase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String  DATABASE_NAME = "todolist";

    public static final SQLiteOpenHelper instance = new ToDoListApplicationDatabase();

    private ToDoListApplicationDatabase() {
        super(ToDoListApplication.getContext() , DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE job(job_id INTEGER PRIMARY KEY AUTOINCREMENT, job_name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS todo_element");
        onCreate(db);
    }
}
