package xyz.takablog.customermanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import xyz.takablog.customermanagementsystem.helper.OpenHelper;

public class SelectStudentActivity extends android.app.ListActivity {

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_student);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SQLiteDatabase database = null;

        try {
            OpenHelper helper = new OpenHelper(SelectStudentActivity.this);
            database = helper.getReadableDatabase();

            cursor = database.query("students", null, null, null, null, null, null);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(SelectStudentActivity.this, R.layout.student_list, cursor, new String[]{"name"}, new int[]{R.id.studentList}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            setListAdapter(adapter);
        }catch (Exception e){
        }
        database.close();
    }
}