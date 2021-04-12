package xyz.takablog.customermanagementsystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import xyz.takablog.customermanagementsystem.helper.OpenHelper;

public class GradesListActivity extends android.app.ListActivity {

    TextView name;
    OpenHelper helper;
    SQLiteDatabase database;
    Cursor cursor;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_list);

        id = getIntent().getLongExtra("id", 0L);

        name = findViewById(R.id.gradesName);

        helper = null;
        database = null;
        cursor = null;
        try {
            helper = new OpenHelper(this);
            database = helper.getReadableDatabase();

            cursor = database.query("grades",
                    null,
                    "_id=?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.grades_list,
                    cursor,
                    new String[]{"testDate",
                            "testName",
                            "english",
                            "math",
                            "japanese",
                            "science",
                            "society",
                            "music",
                            "physical",
                            "techHome",
                            "art",
                            "total5",
                            "total9"},
                    new int[]{R.id.testDate,
                            R.id.testName,
                            R.id.english,
                            R.id.math,
                            R.id.japanese,
                            R.id.science,
                            R.id.society,
                            R.id.music,
                            R.id.physical,
                            R.id.techHome,
                            R.id.art,
                            R.id.total5,
                            R.id.total9},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            setListAdapter(adapter);
        } catch (Exception e) {
            Log.e("表示","エラー");
            e.printStackTrace();
        } finally {
            if (helper != null) {
                helper.close();
            }
            if (database != null) {
                database.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        helper = null;
        database = null;
        cursor = null;

        try {
            helper = new OpenHelper(this);
            database = helper.getReadableDatabase();

            cursor = database.query("students",
                    null,
                    "_id=?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("name");
                String tmp = cursor.getString(columnIndex);
                name.setText(tmp);
            }
        } catch (Exception e) {
            Log.e("表示","エラー");
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
            if (helper != null) {
                helper.close();
            }
        }
    }

    public void back(View view) {
        finish();
    }

}
