package de.dhbw.kassenautomat.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by trugf on 21.04.2016.
 */
public class MySQLLiteOpenHelper extends SQLiteOpenHelper {
    Context c;

    public MySQLLiteOpenHelper(Context c)
    {
        super(c, "kassenautomat", null, 1);
        this.c = c;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table tickets (id int, date DateTime, print_quality int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
