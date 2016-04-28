package de.dhbw.kassenautomat.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
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
    public List<String> getTickets() throws ParseException {
        List<String> strings = new ArrayList<String>();
        
        Cursor c = dbread.rawQuery("select id, date, print_quality, paid from tickets", null);

        if (c.moveToFirst())
            while (!c.isAfterLast())
            {
                char Delimiter = ParkingTicket.getDelimiter();
                int ID = Integer.parseInt(c.getString(0));
                Date Created = ParkingTicket.getSimpleDateFormat().parse(c.getString(1)); //maybe doesn't work
                int printQuality = Integer.parseInt(c.getString(2));
                boolean paid = c.getString(3).toLowerCase() == "true";
                //TODO implement paid-value into ParkingTicket class & saved String

                //This is basically the toString function of ParkingTicket
                strings.add(Integer.toString(ID)+Delimiter+ParkingTicket.getSimpleDateFormat().format(Created)+Delimiter+Integer.toString(printQuality));
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
        Date Created = ticket.getCreated();
        byte printQuality = ticket.getPrintQuality();

        ContentValues values = new ContentValues();
        values.put("date", ParkingTicket.getSimpleDateFormat().format(Created));
        values.put("print_quality", printQuality);
        values.put("paid", false);

        try
        {
            dbwrite.insert("tickets", null, values);
        }
        catch (Exception ex)
        {
            return false;
        }

        return true;
    }

    /**
     * This removes the given ticket from the Database.
     * @param id: ID of the Ticket to be removed
     * @return True when ticket removed successfully.
     */
    public boolean removeTicket(int id)
    {
        try
        {
            dbwrite.delete("tickets", "id=?", new String[]{Integer.toString(id)});
        }
        catch (Exception ex)
        {
            return false;
        }

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
