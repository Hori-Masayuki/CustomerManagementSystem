package xyz.takablog.customermanagementsystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import xyz.takablog.customermanagementsystem.helper.OpenHelper;

public class GradesListActivity extends android.app.ListActivity {

    TextView english, math, japanese, science, society, music, physical, techHome, art, total5, total9, name;
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
        english = findViewById(R.id.english);
        math = findViewById(R.id.math);
        japanese = findViewById(R.id.japanese);
        science = findViewById(R.id.science);
        society = findViewById(R.id.society);
        music = findViewById(R.id.music);
        physical = findViewById(R.id.physical);
        techHome = findViewById(R.id.techHome);
        art = findViewById(R.id.art);
        total5 = findViewById(R.id.total5);
        total9 = findViewById(R.id.total9);

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
                            "art"},
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
                            R.id.art},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            setListAdapter(adapter);
        } catch (Exception e) {
        }
        database.close();
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
        }
        cursor.close();
        database.close();


    }

    public void back(View view) {
        finish();
    }
}