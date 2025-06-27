package com.example.to_do.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;

import androidx.annotation.Nullable;

import com.example.to_do.ToDoModel.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String dbname = "ToDoDB";
    private static final String table = "ToDoTable";
    private static final int version = 1;
    public DBHelper( Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        String qr = "CREATE TABLE ToDoTable(id INTEGER PRIMARY KEY AUTOINCREMENT,task TEXT,status int);";
        db.execSQL(qr);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean insersertTask(ToDoModel toDoModel){
        String task;
        int  status;
        task = toDoModel.getTask();
        status = toDoModel.getStatus();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("task",task);
        cv.put("status",status);
        long insert = db.insert(table, null,cv);
        db.close();
        if (insert == -1){
            return false;
        }else {
            return true;
        }

    }
    public boolean updateTask(int id,String task,int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put("task",task);
        cv.put("status",status);
        int update = db.update("ToDoTable", cv, "ID=?", new String[]{String.valueOf(id)});
        db.close();
        if (update>0){
            return true;
        }else {
            return false;
        }

    }
    public boolean updateStatus(ToDoModel toDoModel){
        int id = toDoModel.getId();
        int status = toDoModel.getStatus();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put("status",status);
        int update = db.update("ToDoTable", cv, "ID=?", new String[]{String.valueOf(id)});
        db.close();
        if (update>0){
            return true;
        }else {
            return false;
        }
    }
    public boolean deleteTask(ToDoModel toDoModel){
        int id = toDoModel.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("ToDoTable", "ID=?", new String[]{String.valueOf(id)});
        db.close();
        if (result == 0){
            return false;
        }else {
            return true;
        }
    }
    public List<ToDoModel> getAllTask(){
        SQLiteDatabase db = this.getWritableDatabase();
        String qr = "SELECT * FROM ToDoTable";
        List<ToDoModel> retunList = new  ArrayList<>();
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(qr, null);
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String task = cursor.getString(1);
                    int status = cursor.getInt(2);
                    ToDoModel toDoModel = new ToDoModel(id, task, status);
                    retunList.add(toDoModel);
                } while (cursor.moveToNext());
            }
            cursor.close();


        }finally {
            db.endTransaction();
        }
        return retunList;

    }
}
