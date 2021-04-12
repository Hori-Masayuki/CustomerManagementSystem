package xyz.takablog.customermanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import xyz.takablog.customermanagementsystem.helper.OpenHelper;

public class UpdateStudentActivity extends AppCompatActivity {

    EditText date, name, ruby, birthday, sex, code, address1, address2, contact, mail, school, year;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

//        EditTextを取得する
        date = findViewById(R.id.date);
        name = findViewById(R.id.name);
        ruby = findViewById(R.id.ruby);
        birthday = findViewById(R.id.birthday);
        sex = findViewById(R.id.sex);
        code = findViewById(R.id.code);
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        contact = findViewById(R.id.contact);
        mail = findViewById(R.id.mail);
        school = findViewById(R.id.school);
        year = findViewById(R.id.year);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        生徒のidをIntentから取得
        id = getIntent().getLongExtra("id", 0L);

        OpenHelper helper = null;
        SQLiteDatabase database = null;
        Cursor cursor = null;

        try {
//            helperとdatabaseを取得
            helper = new OpenHelper(this);
            database = helper.getReadableDatabase();

//            生徒idから生徒情報を取得
            cursor = database.query("students",
                    null,
                    "_id=?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null);
//            生徒情報をEditTextにセットしていく
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("date");
                String tmp = cursor.getString(columnIndex);
                date.setText(tmp);
                columnIndex = cursor.getColumnIndex("name");
                tmp = cursor.getString(columnIndex);
                name.setText(tmp);
                columnIndex = cursor.getColumnIndex("ruby");
                tmp = cursor.getString(columnIndex);
                ruby.setText(tmp);
                columnIndex = cursor.getColumnIndex("birthday");
                tmp = cursor.getString(columnIndex);
                birthday.setText(tmp);
                columnIndex = cursor.getColumnIndex("sex");
                tmp = cursor.getString(columnIndex);
                sex.setText(tmp);
                columnIndex = cursor.getColumnIndex("code");
                tmp = cursor.getString(columnIndex);
                code.setText(tmp);
                columnIndex = cursor.getColumnIndex("address1");
                tmp = cursor.getString(columnIndex);
                address1.setText(tmp);
                columnIndex = cursor.getColumnIndex("address2");
                tmp = cursor.getString(columnIndex);
                address2.setText(tmp);
                columnIndex = cursor.getColumnIndex("contact");
                tmp = cursor.getString(columnIndex);
                contact.setText(tmp);
                columnIndex = cursor.getColumnIndex("mail");
                tmp = cursor.getString(columnIndex);
                mail.setText(tmp);
                columnIndex = cursor.getColumnIndex("school");
                tmp = cursor.getString(columnIndex);
                school.setText(tmp);
                columnIndex = cursor.getColumnIndex("year");
                tmp = cursor.getString(columnIndex);
                year.setText(tmp);
            }
        } catch (Exception e) {
        } finally {
//            クローズ処理
            if (helper != null) {
                helper.close();
            }
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
    }

//    戻るボタンが押されたときに呼び出されるメソッド
    public void back(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.contentNotSaved)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder.show();
    }

//    更新ボタンが押されたときに呼び出されるメソッド
    public void update(View view) {
        SQLiteOpenHelper helperUpdate = null;
        SQLiteDatabase databaseUpdate = null;

//        名前が空だった時の処理
        if (name.getText().toString().length() < 1) {
            Toast.makeText(this, R.string.writeName, Toast.LENGTH_SHORT).show();
            return;
        }
//        郵便番号が空だった時の処理
        if (code.getText().toString().length() < 1) {
            code.setText("0");
        }
        try {
//            入力された値を取得
            String dateText = date.getText().toString();
            String nameText = name.getText().toString();
            String rubyText = ruby.getText().toString();
            String birthdayText = birthday.getText().toString();
            String sexText = sex.getText().toString();
            Integer codeText = Integer.parseInt(code.getText().toString());
            String address1Text = address1.getText().toString();
            String address2Text = address2.getText().toString();
            String contactText = contact.getText().toString();
            String mailText = mail.getText().toString();
            String schoolText = school.getText().toString();
            String yearText = year.getText().toString();

//            helperとdatabaseを取得
            helperUpdate = new OpenHelper(this);
            databaseUpdate = helperUpdate.getWritableDatabase();

//            contentValuesに値をセットしていく
            ContentValues contentValues = new ContentValues();
            contentValues.put("date", dateText);
            contentValues.put("name", nameText);
            contentValues.put("ruby", rubyText);
            contentValues.put("birthday", birthdayText);
            contentValues.put("sex", sexText);
            contentValues.put("code", codeText);
            contentValues.put("address1", address1Text);
            contentValues.put("address2", address2Text);
            contentValues.put("contact", contactText);
            contentValues.put("mail", mailText);
            contentValues.put("school", schoolText);
            contentValues.put("year", yearText);

//            更新処理
            int updateCount = databaseUpdate.update("students",
                    contentValues,
                    "_id=?",
                    new String[]{String.valueOf(id)});

            if (updateCount == 1) {
                Toast.makeText(this, R.string.onUpdate, Toast.LENGTH_SHORT).show();
            }
            finish();
        } catch (Exception e) {
            Toast.makeText(this, R.string.notUpdate, Toast.LENGTH_SHORT).show();
        } finally {
//            クローズ処理
            if (databaseUpdate != null) {
                databaseUpdate.close();
            }
            if (helperUpdate != null) {
                helperUpdate.close();
            }
        }
    }

//    削除を押されたときに呼び出されるメソッド
    public void delete(View view) {
        SQLiteOpenHelper helperDelete = null;
        SQLiteDatabase databaseDelete = null;
        try {
//            helperとdatabaseを取得
            helperDelete = new OpenHelper(this);
            databaseDelete = helperDelete.getWritableDatabase();

//            削除処理
            int deleteCount = databaseDelete.delete("students",
                    "_id=?",
                    new String[]{String.valueOf(id)});

            if (deleteCount == 1) {
                Toast.makeText(this, R.string.onDelete, Toast.LENGTH_SHORT).show();
            }

            finish();
        } catch (Exception e) {
            Toast.makeText(this, R.string.notDelete, Toast.LENGTH_SHORT).show();
        } finally {
//            クローズ処理
            if (helperDelete != null) {
                helperDelete.close();
            }
            if (databaseDelete != null) {
                databaseDelete.close();
            }
        }
    }
}