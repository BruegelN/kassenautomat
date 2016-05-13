package de.dhbw.kassenautomat.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.dhbw.kassenautomat.COIN_DATA;
import de.dhbw.kassenautomat.ParkingTicket;
import de.dhbw.kassenautomat.Receipt;

/**
 * Created by trugf on 20.04.2016.
 */
public class DatabaseManager {
    private SQLiteDatabase dbread, dbwrite;
    private MySQLiteOpenHelper helper;

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

        Cursor c = dbread.rawQuery("select id, created, print_quality, paid from tickets", null);

        if (c.moveToFirst())
            while (!c.isAfterLast())
            {
                char Delimiter = ParkingTicket.getDelimiter();
                int ID = Integer.parseInt(c.getString(0));
                Date Created = ParkingTicket.getSimpleDateFormat().parse(c.getString(1)); //maybe doesn't work
                int printQuality = Integer.parseInt(c.getString(2));
                boolean paid = Boolean.parseBoolean(c.getString(3));

                //This is basically the toString function of ParkingTicket
                strings.add(Integer.toString(ID)+Delimiter+ParkingTicket.getSimpleDateFormat().format(Created)+Delimiter+Integer.toString(printQuality)+Delimiter+Boolean.toString(paid));
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
        values.put("created", ParkingTicket.getSimpleDateFormat().format(Created));
        values.put("print_quality", printQuality);
        values.put("paid", false);

        try
        {
            if (dbwrite.insert("tickets", null, values) == -1)
                return false; // insert returns the newly created row id or -1 on error
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
            if (dbwrite.delete("tickets", "id=?", new String[]{Integer.toString(id)}) !=1)
                return false; //would be a bad thing if more or less than 1 row was affected
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
        finally
        {
            c.close();
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
     * @return The newly set level for the given coin. This may be different from the given new level. -1 on error.
     */
    public int setCoinLevel(int coinValue, int newLevel)
    {
        //Make sure the coin level is positive and does not exceed the MAX_COIN_LVL
        //otherwise set to the nearest accepted value
        newLevel = newLevel> COIN_DATA.MAX_COIN_LVL? COIN_DATA.MAX_COIN_LVL:newLevel;
        newLevel = newLevel<0?0:newLevel;

        ContentValues values = new ContentValues();
        values.put("level", newLevel);

        if (1 != dbwrite.update("coins", values, "value=?", new String[]{Integer.toString(coinValue)}))
            return -1;

        return newLevel;
    }

    /**
     * This will set the paid-status of a ticket and save an receipt into the DB if requested.
     * @param id ID of the ticket.
     * @param price Price that has been paid, only if receipt is requested
     * @param saveReceipt true if receipt should be saved, else false.
     * @return false on error, else true.
     */
    public boolean setTicketPaid(int id, float price, boolean saveReceipt) throws Exception {
        //STEP 1: Set ticket paid in tickets table.
        ContentValues update = new ContentValues();
        update.put("paid", true);

        if (1 != dbwrite.update("tickets", update, "id=?", new String[]{Integer.toString(id)}))
            throw new Exception("NOPE1");

        if (saveReceipt) {
            //STEP 2: Save the receipt
            ContentValues values = new ContentValues();
            values.put("FKid", id);
            values.put("price", (int) (price * 100));
            values.put("paid", new SimpleDateFormat().format(new Date()));
            if (-1 == dbwrite.insert("receipt", null, values))
                throw new Exception("NOPE2");
        }

        return true;
    }

    /**
     * This will give you the receipt corresponding to the given ticket ID.
     * @param ID Ticket id.
     * @return Receipt-object on success, else null.
     */
    public Receipt getReceipt(int ID)
    {
        Cursor c = dbread.rawQuery("SELECT FKid, paid, price from receipt WHERE FKid = " + ID, null);

        if (c.getCount()!=1)
            return null;

        c.moveToFirst();

        int FKid = Integer.parseInt(c.getString(0));
        Date paid;
        try
        {
            paid = new SimpleDateFormat().parse(c.getString(1));
        }
        catch (Exception ex)
        {
            return null;
        }
        float price = (float)Integer.parseInt(c.getString(2))/100;

        // TODO change db to save and load paidPrice and receivedChange
        Receipt rec = new Receipt(FKid, price, 0f, 0f, paid);

        return rec;
    }

    /**
     *  Sets the coin levels to default value.
     */
    public void setDefaultCoinLevels()
    {
        // empty table coins
        dbwrite.delete("coins", null, null);
        // set the default levels as done on DB creation
        helper.setDefaultCoinLevels(dbwrite);
    }

    public void deleteDatabase()
    {
        helper.deleteDB();
    }
}
