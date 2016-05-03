package de.dhbw.kassenautomat.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import de.dhbw.kassenautomat.COIN_DATA;

/**
 * My own SQLLiteOpenHelper, that connects to the DB and creates the needed tables on first use of DB.
 * Created by trugf on 21.04.2016.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    Context c;
    public static final String DB_NAME = "kassenautomat.db";
    public static final int VERSION = 3; //We "start" with DB version 1

    public MySQLiteOpenHelper(Context c)
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
        // CREATE all necessary tables
        db.execSQL("CREATE TABLE tickets (id INTEGER PRIMARY KEY AUTOINCREMENT, created DateTime, print_quality int, paid bool)");
        db.execSQL("CREATE TABLE receipt (FKid int, paid DateTime, price int)");
        db.execSQL("CREATE TABLE coins (value int, level int)");

        // Insert all COINS into the coins table starting with MAX_COIN_LVL
        for (int coin: COIN_DATA.COINS) {
            ContentValues values = new ContentValues();
            values.put("value", coin);
            values.put("level", COIN_DATA.MAX_COIN_LVL/5);

            db.insert("coins", null, values);
        }
    }

    /**
     * We could implement some things here that would be done, when a new version of the DB would be used.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // DELETE the old DB
        c.deleteDatabase(DB_NAME);
        Log.v("DB","DB deleted due to upgrade.");
        // create the new DB as defined above
        onCreate(db);
    }

    /**
     * This obviously deletes the current Database.
     * It's useful for the UnitTests.
     */
    public void deleteDB()
    {
        c.deleteDatabase(DB_NAME);
    }
}
