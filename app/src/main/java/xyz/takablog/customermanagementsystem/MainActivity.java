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

    public void insertStudent(View view) {
        Intent intent = new Intent(MainActivity.this, InsertStudentActivity.class);
        startActivity(intent);
    }

    public void insertGrades(View view) {
        Intent intent = new Intent(MainActivity.this, InsertGradesActivity.class);
        startActivity(intent);
    }

    public void selectStudent(View view) {
        Intent intent = new Intent(MainActivity.this, SelectStudentActivity.class);
        startActivity(intent);
    }

    public void selectGrades(View view) {
        Intent intent = new Intent(MainActivity.this, SelectGradesActivity.class);
        startActivity(intent);
    }
}