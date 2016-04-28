package de.dhbw.kassenautomat.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.dhbw.kassenautomat.COIN_DATA;
import de.dhbw.kassenautomat.ParkingTicket;

/**
 * Created by trugf on 20.04.2016.
 */
public class DatabaseManager {
    public SQLiteDatabase dbread, dbwrite;
    SQLiteOpenHelper helper;

    public DatabaseManager(Context c)
    {
        helper = new MySQLiteOpenHelper(c);

        dbread = helper.getReadableDatabase();
        dbwrite = helper.getWritableDatabase();
    }

    /**
     * This will return all tickets in Database as String-ArrayList.
     * @return String-ArrayList with saved tickets
     */
    public List<String> getTickets()
    {
        List<String> strings = new ArrayList<String>();
        
        Cursor c = dbread.rawQuery("select * from tickets", null);

        if (c.moveToFirst())
            while (!c.isAfterLast())
            {
                strings.add(c.toString());
                //TODO Convert to the string format used by ParkingTicket class
                c.moveToNext();
        }
        c.close();

        return strings;
    }

    /**
     * This saves a ticket in the Database.
     * @param ticket ParkingTicket object
     * @return True when successfully written to Database.
     */
    public boolean saveTicket(ParkingTicket ticket) {
        int ID = ticket.getID();
        Date Created = ticket.getCreated();
        byte printQuality = ticket.getPrintQuality();

        dbwrite.execSQL("insert into tickets (id, date, print_quality, paid) values (" + ID + "," + Created + "," + printQuality + ", FALSE)");
        return true;
    }

    /**
     * This removes the given ticket from the Database.
     * @param ticket Ticket to be removed
     * @return True when ticket removed successfully.
     */
    public boolean removeTicket(ParkingTicket ticket)
    {
        int id = ticket.getID();
        byte printQuality = ticket.getPrintQuality();
        Date Created = ticket.getCreated();

        dbwrite.execSQL("delete from tickets where id = "+id+" and Date = "+ Created+" and print_quality = "+printQuality);
        return true;
    }

    /**
     * Get the current level of a coin by its value.
     * @param cointValue Value of the coin in cents, e.g. 5 = 5ct, 200 = 2 EURO
     * @return The current level of the coin. -1 on error.
     */
    public int getCoinLevel(int cointValue)
    {
        Cursor c = dbread.rawQuery("SELECT level from coins WHERE value = " + cointValue, null);

        if (c.getCount() != 1)
            return -1; // There MUST be only one line matching this query

        c.moveToFirst();

        String value = c.getString(0); // 0: there is only one value selected which has index 0
        int level;

        try
        {
            level = Integer.parseInt(value);
        }
        catch (Exception ex)
        {
            level = -1;
        }

        return level;
    }

    /**
     * This will set the coin level of the given coin value to the given new level.
     * If the given level is higher than the MAX_COIN_LVL it will be set to MAX_COIN_LVL instead.
     * If the given level is lower than 0 it will be set to 0.
     * @param coinValue Value of the coin in cents, e.g. 5 = 5ct, 200 = 2 EURO etc.
     *                  See the COINS-Array for all possible values.
     * @param newLevel The new level to be set.
     * @return The newly set level for the given coin. This may be different from the given new level.
     */
    public int setCoinLevel(int coinValue, int newLevel)
    {
        //Make sure the coin level is positive and does not exceed the MAX_COIN_LVL
        //otherwise set to the nearest accepted value
        newLevel = newLevel> COIN_DATA.MAX_COIN_LVL? COIN_DATA.MAX_COIN_LVL:newLevel;
        newLevel = newLevel<0?0:newLevel;

        ContentValues values = new ContentValues();
        values.put("level", newLevel);

        dbwrite.update("coins", values, "value=?", new String[]{Integer.toString(coinValue)});

        return newLevel;
    }
}
