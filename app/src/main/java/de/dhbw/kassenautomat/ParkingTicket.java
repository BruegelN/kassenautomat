package de.dhbw.kassenautomat;

import android.provider.Telephony;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by trugf on 31.03.2016.
 */
public class ParkingTicket {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
    private Date Created;
    private int ID;
    private static int IDcounter = 0;

    public ParkingTicket()
    {
        Created = new Date();
        ID = IDcounter;
        IDcounter++;
    }

    public ParkingTicket(String savedValue) throws ParseException {
        ID = Integer.parseInt(savedValue.split("#")[0]);
        Created = sdf.parse(savedValue.split("#")[1]);
    }

    public static SimpleDateFormat getSimpleDateFormat()
    {
        return sdf;
    }


    public Date getCreated()
    {
        return Created;
    }

    public int getID()
    {
        return ID;
    }

    @Override
    public String toString() {
        String savedString = ID+"#"+sdf.format(Created);
        return savedString;
    }
}
