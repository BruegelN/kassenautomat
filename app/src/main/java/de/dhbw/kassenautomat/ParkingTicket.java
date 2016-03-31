package de.dhbw.kassenautomat;

import android.provider.Telephony;

import java.util.Date;

/**
 * Created by trugf on 31.03.2016.
 */
public class ParkingTicket {
    private Date Created;
    private int ID;
    private static int IDcounter = 0;

    public ParkingTicket()
    {
        Created = new Date();
        ID = IDcounter;
        IDcounter++;
    }

    public ParkingTicket(String savedValue)
    {
        ID = Integer.parseInt(savedValue.split("#")[0]);
        Created = new Date(savedValue.split("#")[1]);
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
        return ID+"#"+Created;
    }
}
