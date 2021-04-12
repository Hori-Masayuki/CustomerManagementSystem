package xyz.takablog.customermanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import xyz.takablog.customermanagementsystem.helper.AsyncNetworkTask;
import xyz.takablog.customermanagementsystem.helper.OpenHelper;

public class InsertStudentActivity extends AppCompatActivity implements TextWatcher {

    EditText date, name, ruby, birthday, code, address1, address2, contact, mail, school;
    Spinner year, sex;
    AsyncNetworkTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_student);

//        EditTextを取得
        date = findViewById(R.id.date);
        name = findViewById(R.id.name);
        ruby = findViewById(R.id.ruby);
        birthday = findViewById(R.id.birthday);
        sex = findViewById(R.id.sex);
        code = findViewById(R.id.code);
        code.addTextChangedListener(this);
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        contact = findViewById(R.id.contact);
        mail = findViewById(R.id.mail);
        school = findViewById(R.id.school);
        year = findViewById(R.id.year);

//        dateに当日の日付を入力
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        date.setText(sdf.format(today));
    }

    //    戻るボタンが押されたときに呼び出されるメソッド
//    内容が保存されないことを表示してから、ホーム画面へ遷移する
    public void back(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(InsertStudentActivity.this);
        builder.setMessage(R.string.contentNotSaved)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder.show();
    }

    //    入力された値を保存するメソッド
    public void save(View view) {
//        名前が空欄だった時の処理
        if (name.getText().toString().length() == 0) {
            Toast.makeText(this, R.string.writeName, Toast.LENGTH_SHORT).show();
            return;
        }
//        郵便番号が空だった時の処理
        if (code.getText().toString().length() == 0) {
            code.setText("0");
        }
        SQLiteDatabase database = null;
        OpenHelper helper = null;
        try {
//            helperとdatabaseとcontentValuesを取得
            helper = new OpenHelper(InsertStudentActivity.this);
            database = helper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
//            contentValuesに値を入れていく
            contentValues.put("date", date.getText().toString());
            contentValues.put("name", name.getText().toString());
            contentValues.put("ruby", ruby.getText().toString());
            contentValues.put("birthday", birthday.getText().toString());
            contentValues.put("sex", (String) sex.getSelectedItem());
            contentValues.put("address1", address1.getText().toString());
            contentValues.put("address2", address2.getText().toString());
            contentValues.put("code", Integer.parseInt(code.getText().toString()));
            contentValues.put("contact", contact.getText().toString());
            contentValues.put("mail", mail.getText().toString());
            contentValues.put("school", school.getText().toString());
            contentValues.put("year", (String) year.getSelectedItem());
//            databaseに保存する
            database.insert("students", null, contentValues);
        } catch (Exception e) {
//            エラーだった時の処理
            Toast.makeText(InsertStudentActivity.this, R.string.notSave, Toast.LENGTH_SHORT).show();
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
//        保存完了時、トーストを表示
        Toast.makeText(InsertStudentActivity.this, R.string.onSave, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //    郵便番号が7桁になった時に呼び出されるメソッド
    @Override
    public void afterTextChanged(Editable s) {
        String inputCode = s.toString();
//        非同期処理にて、住所を自動で入力する
        if (inputCode.length() == 7) {
            task = new AsyncNetworkTask(InsertStudentActivity.this);
            task.execute("https://zipcloud.ibsnet.co.jp/api/search?zipcode=" + inputCode);
        }
    }
}