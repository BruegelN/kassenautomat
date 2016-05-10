package de.dhbw.kassenautomat;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by trugf on 19.04.2016.
 */
public class TicketManager {

    /**
     * This is the nice constructor of the TicketManager class.
     * @return An instance of TicketManager.
     */
    public TicketManager()
    {
    }

    /**
     * This will create a new Ticket.
     * @return  Function returns the newly created ParkingTicket instance. On error this will be null.
     *          Note that this Ticket instance has no valid id.
     */
    public ParkingTicket createTicket() {
        ParkingTicket newTicket = new ParkingTicket();

        if (MainActivity.getDBmanager().saveTicket(newTicket))
        {
            return newTicket;
        }

        return null; // on error
    }

    /**
     * This will remove a ticket from the current ParkingTicket DB.
     * @param id ID of the Ticket to be removed
     * @return Boolean as defined in DatabaseManager class.
     */
    public boolean removeTicket(int id)
    {
        return MainActivity.getDBmanager().removeTicket(id);
    }

    /**
     * Get the current list of ParkingTickets.
     * @return Current ParkingTicket List (from DB)
     */
    public List<ParkingTicket> getTicketList()
    {
        List<String> savedStrings;
        List<ParkingTicket> parkingTickets = new ArrayList<ParkingTicket>();

        try
        {
            savedStrings = MainActivity.getDBmanager().getTickets();

            for (String savedString:savedStrings)
            {
                parkingTickets.add(new ParkingTicket(savedString));
            }
        }
        catch (Exception ex)
        {
            // on error act as if no ticket could be read
            // TODO: maybe implement some error handling here
            parkingTickets = null;
        }
        finally
        {
            return parkingTickets;
        }
    }
}
