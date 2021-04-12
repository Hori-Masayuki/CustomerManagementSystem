package xyz.takablog.customermanagementsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import xyz.takablog.customermanagementsystem.helper.OpenHelper;

public class InsertGradesActivity extends AppCompatActivity {

    EditText testDate, testName, english, math, japanese, science, society, music, physical, techHome, art;
    Spinner studentId;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_grades);

//        EditTextを取得
        studentId = findViewById(R.id.studentId);
        testDate = findViewById(R.id.testDate);
        testName = findViewById(R.id.testName);
        english = findViewById(R.id.english);
        math = findViewById(R.id.math);
        japanese = findViewById(R.id.japanese);
        science = findViewById(R.id.science);
        society = findViewById(R.id.society);
        music = findViewById(R.id.music);
        physical = findViewById(R.id.physical);
        techHome = findViewById(R.id.techHome);
        art = findViewById(R.id.art);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SQLiteDatabase database = null;
        OpenHelper helper = null;

        try {
//            helperとdatabaseを取得
            helper = new OpenHelper(this);
            database = helper.getReadableDatabase();

//            databaseから取得した値をcursorに入れていく
            cursor = database.query("students",
                    null,
                    null,
                    null,
                    null,
                    null,
                    "year ASC");
//            adapterに情報を入れ、スピナーにセットしていく
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.spinner_item,
                    cursor,
                    new String[]{"year", "name", "_id"},
                    new int[]{R.id.spinnerYear, R.id.spinnerName, R.id.spinnerId},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            studentId.setAdapter(adapter);
        } catch (Exception e) {
        } finally {
//            クローズ処理
            if (database != null) {
                database.close();
            }
            if (helper != null) {
                helper.close();
            }
        }
    }

//    戻るボタンが押されたときに呼び出されるメソッド
    public void back(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.contentNotSaved)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.show();
    }

//    保存が押されたときに呼び出されるメソッド
    public void save(View view) {

        SQLiteDatabase database = null;
        OpenHelper helper = null;

//        テスト日、テスト名が空だった時の処理
        if (testDate.getText().toString().length() < 1 || testName.getText().toString().length() < 1) {
            Toast.makeText(this, R.string.writeTestInfo, Toast.LENGTH_SHORT).show();
            return;
        }
//        点数がそれぞれ空だった時に0を代入する
        if (english.getText().toString().length() < 1) {
            english.setText("0");
        }
        if (math.getText().toString().length() < 1) {
            math.setText("0");
        }
        if (japanese.getText().toString().length() < 1) {
            japanese.setText("0");
        }
        if (science.getText().toString().length() < 1) {
            science.setText("0");
        }
        if (society.getText().toString().length() < 1) {
            society.setText("0");
        }
        if (music.getText().toString().length() < 1) {
            music.setText("0");
        }
        if (physical.getText().toString().length() < 1) {
            physical.setText("0");
        }
        if (techHome.getText().toString().length() < 1) {
            techHome.setText("0");
        }
        if (art.getText().toString().length() < 1) {
            art.setText("0");
        }
        try {
//            入力された値を取得
            Integer studentIdText = Integer.parseInt(((TextView) findViewById(R.id.spinnerId)).getText().toString());
            String testDateText = testDate.getText().toString();
            String testNameText = testName.getText().toString();
            Integer englishText = Integer.parseInt(english.getText().toString());
            Integer mathText = Integer.parseInt(math.getText().toString());
            Integer japaneseText = Integer.parseInt(japanese.getText().toString());
            Integer scienceText = Integer.parseInt(science.getText().toString());
            Integer societyText = Integer.parseInt(society.getText().toString());
            Integer musicText = Integer.parseInt(music.getText().toString());
            Integer physicalText = Integer.parseInt(physical.getText().toString());
            Integer techHomeText = Integer.parseInt(techHome.getText().toString());
            Integer artText = Integer.parseInt(art.getText().toString());
            Integer total5 = englishText + mathText + japaneseText + scienceText + societyText;
            Integer total9 = total5 + musicText + physicalText + techHomeText + artText;

//            helperとdatabaseを取得
            helper = new OpenHelper(this);
            database = helper.getWritableDatabase();

//            contentValuesに値をセットしていく
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", studentIdText);
            contentValues.put("testDate", testDateText);
            contentValues.put("testName", testNameText);
            contentValues.put("japanese", japaneseText);
            contentValues.put("math", mathText);
            contentValues.put("science", scienceText);
            contentValues.put("society", societyText);
            contentValues.put("english", englishText);
            contentValues.put("music", musicText);
            contentValues.put("physical", physicalText);
            contentValues.put("art", artText);
            contentValues.put("techHome", techHomeText);
            contentValues.put("total5", total5);
            contentValues.put("total9", total9);

//            databaseに保存
            database.insert("grades", null, contentValues);
        } catch (Exception e) {
            Toast.makeText(this, R.string.notSave, Toast.LENGTH_SHORT).show();
            return;
        } finally {
//            クローズ処理
            if (database != null) {
                database.close();
            }
            if (helper != null) {
                helper.close();
            }
        }
//        トースト表示
        Toast.makeText(this, R.string.onSave, Toast.LENGTH_SHORT).show();
        finish();
    }
}