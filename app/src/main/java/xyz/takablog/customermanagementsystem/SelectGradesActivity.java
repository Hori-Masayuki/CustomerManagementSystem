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

//    戻るボタンが押されたときに呼び出されるメソッド
    public void back(View view) {
        finish();
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

//            databaseの検索結果をcursorで取得
            cursor = database.query("students",
                    null,
                    null,
                    null,
                    null,
                    null,
                    "year ASC");
//            adapterに情報を入れて、リストにセットする
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.student_list,
                    cursor,
                    new String[]{"name", "year"},
                    new int[]{R.id.listName, R.id.listYear},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            setListAdapter(adapter);
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

//    生徒のidを持って、成績一覧画面へ遷移する
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, GradesListActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}