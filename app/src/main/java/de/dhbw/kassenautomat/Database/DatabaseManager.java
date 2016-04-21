package de.dhbw.kassenautomat.Database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.dhbw.kassenautomat.ParkingTicket;

/**
 * Created by trugf on 20.04.2016.
 */
public class DatabaseManager {
    SQLiteDatabase dbread, dbwrite;
    MySQLLiteOpenHelper helper;

    public DatabaseManager(Context c)
    {
        helper = new MySQLLiteOpenHelper(c);
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

        return strings;
    }

    /**
     * This saves a ticket in the Database.
     * @param ticket Ticket in one string.
     * @return True when successfully written to Database.
     */
    public boolean saveTicket(String ticket) {
        String Del = Character.toString(ParkingTicket.getDelimiter());
        String[] splittedValues = ticket.split(Del);

        int ID = Integer.parseInt(splittedValues[0]);
        Date Created;

        //get rid of the annoying parse exception by returning false on its occurrence.
        try { Created = ParkingTicket.getSimpleDateFormat().parse(splittedValues[1]); }
        catch (ParseException ex) { return false; }

        byte printQuality = (byte)Integer.parseInt(splittedValues[2]);

        dbwrite.execSQL("insert into tickets (id, date, print_quality) values ("+ID+","+Created+","+printQuality+")");
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
}
