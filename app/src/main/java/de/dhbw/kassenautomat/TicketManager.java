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
     * @return Function returns the newly created ParkingTicket instance.
     */
    public ParkingTicket createTicket() {
        ParkingTicket newTicket = new ParkingTicket();

        MainActivity.getDBmanager().saveTicket(newTicket);

        return newTicket;
    }

    /**
     * This will remove a ticket from the current ParkingTicket list.
     * @param ticket Ticket to be removed
     */
    public void removeTicket(ParkingTicket ticket)
    {
        MainActivity.getDBmanager().removeTicket(ticket);
    }

    /**
     * Get the current list of ParkingTickets.
     * @return Current ParkingTicket List
     */
    public List<String> getTicketList()
    {
        return MainActivity.getDBmanager().getTickets();
    }
}
