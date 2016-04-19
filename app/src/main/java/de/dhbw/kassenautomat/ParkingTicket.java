package de.dhbw.kassenautomat;

import android.provider.Telephony;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by trugf on 31.03.2016.
 */
public class ParkingTicket {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
    private static final char Delimeter = ';';
    private static int IDcounter = 0;

    private Date Created;
    private int ID;
    private byte printQuality; //byte is enough for printquality numbers ranging from 80 to 100

    public ParkingTicket()
    {
        Created = new Date();
        ID = IDcounter;
        printQuality= generatePrintQuality();

        IDcounter++;
    }

    public ParkingTicket(String savedValue) throws ParseException {
        String Del = Character.toString(Delimeter);
        String[] splittedValues = savedValue.split(Del);

        ID = Integer.parseInt(splittedValues[0]);
        Created = sdf.parse(splittedValues[1]);
        printQuality = (byte)Integer.parseInt(splittedValues[2]);
    }

    private byte generatePrintQuality()
    {
        Random ran = new Random();
        return (byte)(ran.nextInt(21)+80);
    }
    public static SimpleDateFormat getSimpleDateFormat()
    {
        return sdf;
    }
    public static char getDelimeter()
    {
        return Delimeter;
    }

    public Date getCreated()
    {
        return Created;
    }

    public int getID()
    {
        return ID;
    }

    public int getPrintQuality()
    {
        return printQuality;
    }

    @Override
    public String toString() {
        String savedString = Integer.toString(ID) + Delimeter + sdf.format(Created) + Delimeter + Integer.toString(printQuality);
        return savedString;
    }
}
