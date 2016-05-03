package de.dhbw.kassenautomat;

import org.junit.Test;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.*;

public class UnitTestParkingTicket
{
    ParkingTicket TestTicket = new ParkingTicket();
    ParkingTicket TestTicket2 = new ParkingTicket();

    @Test
    public void CheckDate() throws InterruptedException
    {
        Date before = new Date();
        Thread.sleep(1000);
        Date TicketDate = new ParkingTicket().getCreated();
        Thread.sleep(1000);
        Date after = new Date();

        assertTrue(TicketDate.after(before) && TicketDate.before(after));
    }

    @Test
    public void CheckToStringMethod()
    {
        char Delimeter = ParkingTicket.getDelimiter();
        String saved = Integer.toString(TestTicket.getID())+Delimeter+ParkingTicket.getSimpleDateFormat().format(TestTicket.getCreated())+Delimeter+Integer.toString(TestTicket.getPrintQuality())+Delimeter+ Boolean.toString(TestTicket.getPaid());

        assertTrue(TestTicket.toString() + "<>" + saved, TestTicket.toString().equals(saved));
    }

    @Test
    public void CheckContructionWithSavedString() throws ParseException
    {
        String savedString = TestTicket.toString();

        ParkingTicket ReloadedTicket = new ParkingTicket(savedString);

        assertTrue(ReloadedTicket.toString() + " " + TestTicket.toString(),
                ParkingTicket.getSimpleDateFormat().format(ReloadedTicket.getCreated()).equals(ParkingTicket.getSimpleDateFormat().format(TestTicket.getCreated())));

        assertTrue(ReloadedTicket.getPrintQuality() == TestTicket.getPrintQuality());
    }

    @Test
    public void CheckRandomNumberGeneration()
    {
        for (int i = 0; i<100; i++) //create some 100 new Tickets and check whether the print Quality is in range...
        {
            ParkingTicket newTicket = new ParkingTicket();
            assertTrue(Integer.toString(newTicket.getPrintQuality()), (80<=newTicket.getPrintQuality()) && (newTicket.getPrintQuality()<=100));
        }
    }
}