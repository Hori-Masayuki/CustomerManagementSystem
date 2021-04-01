package xyz.takablog.customermanagementsystem.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DATABASE";
    public static final int VERSION = 2;
    public static final String CREATE_TABLE_STUDENT =
            "CREATE TABLE 'students' ('_id' INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    " 'date' TEXT ," +
                    " 'name' TEXT," +
                    " 'ruby' TEXT," +
                    " 'birthday' TEXT ," +
                    " 'sex' TEXT," +
                    " 'address1' TEXT," +
                    "'address2' TEXT ," +
                    " 'code' INTEGER ," +
                    " 'contact' TEXT," +
                    " 'mail' TEXT," +
                    " 'school' TEXT," +
                    " 'year' TEXT);";
    public static final String CREATE_TABLE_GRADES =
            "CREATE TABLE 'grades' ('grades_id' INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    " '_id' INTEGER ," +
                    " 'testDate' TEXT," +
                    " 'testName' TEXT," +
                    "'japanese' INTEGER," +
                    "'math' INTEGER," +
                    "'science' INTEGER," +
                    "'society' INTEGER," +
                    "'english' INTEGER," +
                    "'total5' INTEGER,"+
                    "'total9' INTEGER,"+
                    "'music' INTEGER," +
                    "'physical' INTEGER," +
                    "'art' INTEGER," +
                    "'techHome' INTEGER);";
    public static final String DROP_TABLE_STUDENT = "drop table students;";
    public static final String DROP_TABLE_GRADES = "drop table grades;";

    public OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENT);
        db.execSQL(CREATE_TABLE_GRADES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_STUDENT);
        db.execSQL(DROP_TABLE_GRADES);
        onCreate(db);
    }
}
