package de.dhbw.kassenautomat;

import org.junit.Test;

import java.lang.Integer;
import java.lang.String;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import de.dhbw.kassenautomat.ParkingTicket;
import de.dhbw.kassenautomat.TicketManager;

import static org.junit.Assert.*;

public class UnitTestTicketManager
{
    TicketManager myTicketManager = new TicketManager();

    @Test
    public void CheckTicketCreation()
    {
        ParkingTicket TestTicket = myTicketManager.createTicket(); //create 1 new ticket

        assertTrue(Integer.toString(myTicketManager.getTicketList().size()), myTicketManager.getTicketList().size() == 1); //now there should be one ticket in the list
    }

    @Test
    public void CkeckTicketCreationFromSavedStringList() throws ParseException
    {
        List<String> someTicketList = Arrays.asList("1;01.01.2016-12:00:00;90", "2;01.01.2016-12:01:00;90"); //funny list with two tickets

        TicketManager my2ndTicketManager = new TicketManager(someTicketList);

        assertTrue(Integer.toString(my2ndTicketManager.getTicketList().size()), my2ndTicketManager.getTicketList().size() == 2); //there should be two tickets
    }


}