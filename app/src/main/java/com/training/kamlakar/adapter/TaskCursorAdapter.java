package com.training.kamlakar.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.training.kamlakar.mytodoapp.R;

/**
 * Created by patilk on 2/9/2017.
 */

public class TaskCursorAdapter extends CursorAdapter {

    public TaskCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        // TextView tvId = (TextView) view.findViewById(R.id.tvId);
        TextView tvTaskTitle = (TextView) view.findViewById(R.id.tvTaskTitle);
        // CheckBox cbStatus = (CheckBox) view.findViewById(R.id.cbStatus);

        // tvId.setText(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
        // tvId.setText(cursor.getString(cursor.getColumnIndex("_id")));
        tvTaskTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow("title")));

//        int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));
//        boolean taskStatus = false;
//        if ( status == 1 ) {
//            taskStatus = true;
//        }
        //cbStatus.setActivated(taskStatus);

//        ListView lvItems = (ListView) view.findViewById(R.id.lvItems);
//        lvItems.setOnItemLongClickListener(
//                new AdapterView.OnItemLongClickListener() {
//                    @Override
//                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                        System.out.println("position clicked = " + position);
//                        //Task task = (Task) parent.getItemAtPosition(position);
//                        //final String taskId = cursor.getString(cursor.getColumnIndex("_id"));
//                        Toast.makeText(view.getContext(), "Clicked on " + position + " ", Toast.LENGTH_SHORT).show();
//                        return true;
//                    }
//                }
//        );

    }


}
