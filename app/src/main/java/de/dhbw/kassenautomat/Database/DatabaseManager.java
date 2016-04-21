package de.dhbw.kassenautomat.Database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.dhbw.kassenautomat.ParkingTicket;
import de.dhbw.kassenautomat.TicketManager;

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

    public List<String> getTickets()
    {
        List<String> strings = new ArrayList<String>();
        
        Cursor c = dbread.rawQuery("select * from tickets", null);

        if (c.moveToFirst())
            while (!c.isAfterLast())
            {
                strings.add(c.toString());
                c.moveToNext();
        }

        return strings;
    }

    public boolean saveTicket(String ticket) throws ParseException {
        String Del = Character.toString(ParkingTicket.getDelimiter());
        String[] splittedValues = ticket.split(Del);

        int ID = Integer.parseInt(splittedValues[0]);
        Date Created = ParkingTicket.getSimpleDateFormat().parse(splittedValues[1]);
        byte printQuality = (byte)Integer.parseInt(splittedValues[2]);

        dbwrite.execSQL("insert into tickets (id, date, print_quality) values ("+ID+","+Created+","+printQuality+")");
        return true;
    }

    public boolean removeTicket(ParkingTicket ticket)
    {
        int id = ticket.getID();
        byte printQuality = ticket.getPrintQuality();
        Date Created = ticket.getCreated();

        dbwrite.execSQL("delete from tickets where id = "+id+" and Date = "+ Created+" and print_quality = "+printQuality);
        return true;
    }
}
