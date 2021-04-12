package xyz.takablog.customermanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //    新規生徒登録画面へ遷移するメソッド
    public void insertStudent(View view) {
        Intent intent = new Intent(MainActivity.this, InsertStudentActivity.class);
        startActivity(intent);
    }

    //    成績データを登録する画面へ遷移するメソッド
    public void insertGrades(View view) {
        Intent intent = new Intent(MainActivity.this, InsertGradesActivity.class);
        startActivity(intent);
    }

    //    生徒一覧画面へ遷移するメソッド
    public void selectStudent(View view) {
        Intent intent = new Intent(MainActivity.this, SelectStudentActivity.class);
        startActivity(intent);
    }

    //    成績一覧画面へ行くメソッド
    public void selectGrades(View view) {
        Intent intent = new Intent(MainActivity.this, SelectGradesActivity.class);
        startActivity(intent);
    }
}