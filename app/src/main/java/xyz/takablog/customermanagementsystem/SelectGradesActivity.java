package xyz.takablog.customermanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import xyz.takablog.customermanagementsystem.helper.OpenHelper;

public class SelectGradesActivity extends android.app.ListActivity {

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_grades);
    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SQLiteDatabase database = null;
        OpenHelper helper = null;

        try {
            helper = new OpenHelper(this);
            database = helper.getReadableDatabase();

            cursor = database.query("students",
                    null,
                    null,
                    null,
                    null,
                    null,
                    "year ASC");
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.student_list,
                    cursor,
                    new String[]{"name", "year"},
                    new int[]{R.id.listName, R.id.listYear},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            setListAdapter(adapter);
        } catch (Exception e) {
        } finally {
            if (database != null) {
                database.close();
            }
            if (helper != null) {
                helper.close();
            }
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, GradesListActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}