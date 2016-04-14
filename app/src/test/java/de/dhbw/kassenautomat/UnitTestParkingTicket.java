package de.dhbw.kassenautomat;

import junit.framework.Assert;

import org.junit.Test;

import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.System;
import java.text.ParseException;
import java.util.Date;

import dalvik.annotation.TestTargetClass;
import de.dhbw.kassenautomat.ParkingTicket;
import de.dhbw.kassenautomat.TestingDemo;

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
    public void CheckPositiveID()
    {
        assertTrue(TestTicket.getID() >= 0);
    }

    @Test
    public void CheckIDcounter()
    {
        int ID = TestTicket.getID();
        int ID2 = TestTicket2.getID();

        assertTrue(ID2 == ID+1);
    }

    @Test
    public void CheckToStringMethod()
    {
        char Delimeter = ParkingTicket.getDelimeter();
        String saved = Integer.toString(TestTicket.getID())+Delimeter+ParkingTicket.getSimpleDateFormat().format(TestTicket.getCreated())+Delimeter+Integer.toString(TestTicket.getPrintQuality());

        assertTrue(TestTicket.toString() + "<>" + saved, TestTicket.toString().equals(saved));
    }

    @Test
    public void CheckContructionWithSavedString() throws ParseException
    {
        String savedString = TestTicket.toString();

        ParkingTicket ReloadedTicket = new ParkingTicket(savedString);

        assertTrue(ReloadedTicket.toString() + " " + TestTicket.toString(),
                ParkingTicket.getSimpleDateFormat().format(ReloadedTicket.getCreated()).equals(ParkingTicket.getSimpleDateFormat().format(TestTicket.getCreated())));

        assertTrue(ReloadedTicket.getID() == TestTicket.getID());

        assertTrue(ReloadedTicket.getPrintQuality() == TestTicket.getPrintQuality());
    }
}