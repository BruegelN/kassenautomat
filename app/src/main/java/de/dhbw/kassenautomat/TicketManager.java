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
    private List<ParkingTicket> ParkingTickets = new LinkedList<ParkingTicket>();

    /**
     * This is the nice constructor of the TicketManager Class.
     * @return An instance of TicketManager.
     */
    public TicketManager()
    {
    }

    /**
     * This is another Constructor of the TicketManager Class
     * It will reconstruct ParkingTickets from a list of saved strings.
     * @param savedStrings A list of saved strings for ParkingTickets.
     * @throws ParseException This should not happen as long as the saved strings have the right syntax.
     */
    public TicketManager(List<String> savedStrings) throws ParseException {
        for (String saved:savedStrings)
        {
            ParkingTickets.add(new ParkingTicket(saved));
        }
    }

    /**
     * This will create a new Ticket.
     * @return Function returns the newly created ParkingTicket instance.
     */
    public ParkingTicket createTicket() {
        ParkingTicket newTicket;
        ParkingTickets.add(newTicket = new ParkingTicket());

        //TODO save ticket in some android storage

        return newTicket;
    }

    /**
     * This will remove a ticket from the current ParkingTicket list.
     * @param ticket Ticket to be removed
     */
    public void removeTicket(ParkingTicket ticket)
    {
        ParkingTickets.remove(ticket);
        //TODO remove ticket from android storage
    }

    /**
     * Get the current list of ParkingTickets.
     * @return Current ParkingTicket List
     */
    public List<ParkingTicket> getTicketList() {
        return ParkingTickets;
    }
}
