package com.training.kamlakar.mytodoapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.training.kamlakar.adapter.TaskCursorAdapter;
import com.training.kamlakar.db.TaskDbHelper;
import com.training.kamlakar.model.Task;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ToDoActivity extends AppCompatActivity {
    private final int EDIT_REQUEST_CODE = 200;

    protected TaskDbHelper dbHelper;

    ArrayList<String> items;
    // ArrayAdapter<String> itemsAdapter;
    TaskCursorAdapter taskCursorAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        lvItems = (ListView) findViewById(R.id.lvItems);
        // items = new ArrayList<>();
        // readItems();

        // read items from database
        dbHelper = new TaskDbHelper(this);
        Cursor tasksCursor = dbHelper.getAllTasks();

//        List<Task> taskList = dbHelper.getAllTasks();
//        items = new ArrayList<String>();
//        for (Task t : taskList) {
//            items.add(t.getTaskId() + ": " + t.getTaskTitle());
//        }

//        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//        lvItems.setAdapter(itemsAdapter);
        // items.add("First Item");
        // items.add("Second Item");

        taskCursorAdapter = new TaskCursorAdapter(this, tasksCursor);
        lvItems.setAdapter(taskCursorAdapter);



        setupListViewListener(); // list for remove
        setupEditListener(); // listen for edit
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println("position clicked = " + position);
                        // Task task = (Task) parent.getItemAtPosition(position);
                        Toast.makeText(view.getContext(), "Deleted task " + id + " ", Toast.LENGTH_SHORT).show();
                        dbHelper.deleteTask(id);

                        Cursor updatedTasksCursor = dbHelper.getAllTasks();
                        taskCursorAdapter.changeCursor(updatedTasksCursor);
                        // items.remove(position);
                        // itemsAdapter.notifyDataSetChanged();
                        // writeItems();
                        return true;
                    }
                }
        );
    }



    private void setupEditListener() {

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
                        System.out.println("position clicked = " + position + " and id = " + id);
                        Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                        i.putExtra("id", id);
                        i.putExtra("position", position);
                        //i.putExtra("id",lvItems.getId());
                        i.putExtra("text", cursor.getString(cursor.getColumnIndex("title")));

                        startActivityForResult(i, EDIT_REQUEST_CODE);
                    }
                }
        );
    }

    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        // itemsAdapter.add(itemText);
        etNewItem.setText("");

        // save to database as well
        Task newTask = new Task(itemText, 0); // status = 0 -> pending, 1 -> done
        dbHelper.addTask(newTask);

        Cursor updatedTasksCursor = dbHelper.getAllTasks();
        taskCursorAdapter.changeCursor(updatedTasksCursor);
        // writeItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE ) {
            String updatedValue = data.getExtras().getString("EditedText");

            int position = data.getExtras().getInt("position");
            long id = data.getExtras().getLong("id");

            System.out.println("Edited text : " + updatedValue);
            System.out.println("ID : " + id);
            System.out.println("Position : " + position);

            Task editedTask = new Task();
            editedTask.set_id(id);
            editedTask.setTaskTitle(updatedValue);
            editedTask.setTaskStatus(0); // 0 for now

            dbHelper.editTask(editedTask); // update the db

            // update the listview
            Cursor updatedTasksCursor = dbHelper.getAllTasks();
            taskCursorAdapter.changeCursor(updatedTasksCursor);

            //lvItems.s
            // items.set(position, updatedValue);
            // itemsAdapter.notifyDataSetChanged();
            // writeItems();
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(toDoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File totoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(totoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
