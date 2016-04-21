package de.dhbw.kassenautomat.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * My own SQLLiteOpenHelper, that connects to the DB and creates the needed tables on first use of DB.
 * Created by trugf on 21.04.2016.
 */
public class MySQLLiteOpenHelper extends SQLiteOpenHelper {
    Context c;
    private static final String DB_NAME = "kassenautomat";
    private static final int VERSION = 1; //We "start" with DB version 1

    public MySQLLiteOpenHelper(Context c)
    {
        super(c, DB_NAME, null, VERSION);
        this.c = c;
    }

    /**
     * Creates our DB tables onCreate of the DB.
     * @param db Is some variable defined in SQLiteOpenHelper
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE tickets (id int, created DateTime, print_quality int, paid bool)");
        db.execSQL("CREATE TABLE receipt (FKid int, paid DateTime)");
        db.execSQL("CREATE TABLE coins (value int, level int)");
    }

    /**
     * We could implement some things here that would be done, when a new version of the DB would be used.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // I don't see how this database would ever be upgraded.
    }
}
