package de.dhbw.kassenautomat;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by trugf on 31.03.2016.
 */
public class ParkingTicket implements Serializable{

    // field which is used by java serializable runtime to identify an object on deserialization.
    private static final long serialVersionUID = 42l; // don't question it!
    public  static final  String SERIAL_KEY = "ParkingTicketSerial";

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
    private static final char Delimiter = ';';

    private int ID;
    private Date Created;
    private byte printQuality; //byte is enough for print quality numbers ranging from 80 to 100
    private boolean paid;

    /**
    *   This is the nice constructor of the ParkingTicket class.
    *   It sets the creation timestamp, id and generates the print quality of the new ParkingTicket.
    *   @return Object of type ParkingTicket
    */
    public ParkingTicket()
    {
        ID = -1; // some kind of dummy ID since the real one will be autoincremented by database
        Created = new Date();
        printQuality= generatePrintQuality();
        paid = false;
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

        this.ID = Integer.parseInt(splittedValues[0]); // the real ID given by db autoincrement will be set here
        this.Created = sdf.parse(splittedValues[1]);
        this.printQuality = (byte)Integer.parseInt(splittedValues[2]);
        this.paid = Boolean.parseBoolean(splittedValues[3]);
    }

    public ParkingTicket(int id, byte printQuality, Date Created)
    {
        this.ID = id;
        this.printQuality = printQuality;
        this.Created = Created;
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
    *   @return This will return the delimiter (char) used by the ParkingTicket class to separate strings.
     */
    public static char getDelimiter()
    {
        return Delimiter;
    }

    /**
     *   @return This will return the ID (Integer) of this instance.
     */
    public int getID()
    {
        return ID;
    }

    /**
    *   @return This will return the creation timestamp (Date) of this instance.
     */
    public Date getCreated()
    {
        return Created;
    }

    /**
    *   @return This will return a number (byte) between 80 and 100 which represents the print quality of this instance.
     */
    public byte getPrintQuality()
    {
        return printQuality;
    }

    /**
     * @return This will return the boolean telling you whether this ticket has been paid for already.
     */
    public boolean getPaid() {
        return paid;
    }

    @Override
    /**
    *   @return This will return a string that represents this instance. The string can be used for the reconstruction of an instance.
     */
    public String toString() {
        String savedString = Integer.toString(ID) + Delimiter + sdf.format(Created) + Delimiter + Integer.toString(printQuality)+Delimiter+Boolean.toString(paid);
        return savedString;
    }
}
