package de.dhbw.kassenautomat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by trugf on 31.03.2016.
 */
public class ParkingTicket {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
    private static final char Delimiter = ';';
    private static int IDcounter = 0;

    private Date Created;
    private int ID;
    private byte printQuality; //byte is enough for print quality numbers ranging from 80 to 100

    /**
    *   This is the nice constructor of the ParkingTicket class.
    *   It sets the creation timestamp, id and generates the print quality of the new ParkingTicket.
    *   @return Object of type ParkingTicket
    */
    public ParkingTicket()
    {
        Created = new Date();
        ID = IDcounter;
        printQuality= generatePrintQuality();

        IDcounter++;
    }

    /**
    *   This is an alternative constructor of the ParkingTicket class.
    *   It will recreate a ParkingTicket instance by the given saved value.
    *   @param  savedValue (string) - The saved string of another instance of ParkingTicket. Usually created by the toString-method of this class.
    *   @return Object of type ParkingTicket
    */
    public ParkingTicket(String savedValue) throws ParseException {
        String Del = Character.toString(Delimiter);
        String[] splittedValues = savedValue.split(Del);

        ID = Integer.parseInt(splittedValues[0]);
        Created = sdf.parse(splittedValues[1]);
        printQuality = (byte)Integer.parseInt(splittedValues[2]);
    }

    /**
    *   This function will generate a random number in the range from 80 to 100.
    *   @return random number (byte) between 80 and 100
    */
    private byte generatePrintQuality()
    {
        Random ran = new Random();
        return (byte)(ran.nextInt(21)+80);
    }

    /**
    *   @return This will return the SimpleDateFormat used by the ParkingTicket class to represent dates.
    */
    public static SimpleDateFormat getSimpleDateFormat()
    {
        return sdf;
    }

    /**
    *   @return This will return the delimiter (char) used by the ParkingTicket class to separate
     */
    public static char getDelimiter()
    {
        return Delimiter;
    }

    /**
    *   @return This will return the creation timestamp (Date) of this instance.
     */
    public Date getCreated()
    {
        return Created;
    }

    /**
    *   @return This will return the ID (Integer) of this instance.
     */
    public int getID()
    {
        return ID;
    }

    /**
    *   @return This will return a number (byte) between 80 and 100 which represents the print quality of this instance.
     */
    public byte getPrintQuality()
    {
        return printQuality;
    }

    @Override
    /**
    *   @return This will return a string that represents this instance. The string can be used for the reconstruction of an instance.
     */
    public String toString() {
        String savedString = Integer.toString(ID) + Delimiter + sdf.format(Created) + Delimiter + Integer.toString(printQuality);
        return savedString;
    }
}
