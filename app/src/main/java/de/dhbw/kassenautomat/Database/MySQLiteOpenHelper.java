package de.dhbw.kassenautomat.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * My own SQLLiteOpenHelper, that connects to the DB and creates the needed tables on first use of DB.
 * Created by trugf on 21.04.2016.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    Context c;
    private static final String DB_NAME = "kassenautomat.db";
    private static final int VERSION = 1; //We "start" with DB version 1

    // TODO maybe this stuff should be defined elsewhere, since this is not only needed in the DB
    public static final int MAX_COIN_LVL = 200;
    public static final int[] COINS = {5, 10, 20, 50, 100, 200};

    public MySQLiteOpenHelper(Context c)
    {
        super(c, null /*in MEMORY for TEST*/, null, VERSION);
        this.c = c;
    }

    /**
     * Creates our DB tables onCreate of the DB.
     * @param db Is some variable defined in SQLiteOpenHelper
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        /*
        // CREATE all necessary tables
        db.execSQL("CREATE TABLE tickets (id int, created DateTime, print_quality int, paid bool)");
        db.execSQL("CREATE TABLE receipt (FKid int, paid DateTime)");
        db.execSQL("CREATE TABLE coins (value int, level int)");

        // Insert all COINS into the coins table starting with MAX_COIN_LVL
        for (int coin: COINS) {
            ContentValues values = new ContentValues();
            values.put("value", coin);
            values.put("level", MAX_COIN_LVL);

            db.insert("coins", null, values);
        }
        */
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
        // create the new DB as defined above
        onCreate(db);
    }
}
