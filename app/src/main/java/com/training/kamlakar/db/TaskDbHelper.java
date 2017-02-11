package com.training.kamlakar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.training.kamlakar.model.Task;

/**
 * Created by patilk on 2/8/2017.
 */

public class TaskDbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "taskmanager";
    private static final String TABLE_TASKS = "tasks"; // table name
    // column names
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TASK_TITLE = "title";
    private static final String COLUMN_STATUS = "status";

    public TaskDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TASK_TITLE + " TEXT, "
                + COLUMN_STATUS + " INTEGER)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
        db.close();
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // add a new task
    public void addTask(Task task) {
        // get db handle
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_TITLE, task.getTaskTitle());
        values.put(COLUMN_STATUS, task.getTaskStatus());

        // insert row into database
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    // edit Task
    public void editTask(Task task) {
        // updating row
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_TITLE, task.getTaskTitle()); // task name
        values.put(COLUMN_STATUS, task.getTaskStatus());

        db.update(TABLE_TASKS, values, COLUMN_ID + " = ?",new String[] {String.valueOf(task.get_id())});
        db.close();
    }

    // remove Task
    public void deleteTask(Long taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_ID + " = ?",new String[] {String.valueOf(taskId)});
        db.close();
    }

    // get all tasks from db
    public Cursor getAllTasks() {
        // List<Task> taskList = new ArrayList<Task>();

        String selectAllQuery = "select rowid _id,* from " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectAllQuery, null);

        return cursor;

        // looping through all rows and add to list
//        if (cursor.moveToFirst()) {
//            do {
//                Task task = new Task();
//                task.setTaskId(cursor.getInt(0));
//                task.setTaskTitle(cursor.getString(1));
//                task.setTaskStatus(cursor.getInt(2));
//                taskList.add(task);
//            } while (cursor.moveToNext());
//        }

        // return taskList;
    }
}
