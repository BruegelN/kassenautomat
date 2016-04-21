package de.dhbw.kassenautomat;

import android.app.Activity;
import android.app.Application;

import org.junit.Test;

import java.lang.Integer;
import java.lang.String;
import java.util.List;
import static org.junit.Assert.*;
import de.dhbw.kassenautomat.Database.DatabaseManager;
import de.dhbw.kassenautomat.FullscreenActivity;
import de.dhbw.kassenautomat.ParkingTicket;
import static org.mockito.Mockito.*;


public class UnitTestDatabaseManager{

    //TODO This shit ain't working cause "cannot find symbol" mock
    /*
    @Test
    public void TestTicketsTable()
    {
        FullscreenActivity activity = mock(FullscreenActivity());
        DatabaseManager dbm = new DatabaseManager(activity.getApplicationContext());

        List<String> myTickets = dbm.getTickets();
        assertTrue(Integer.toString(myTickets.size()), myTickets.size() == 1);
    }
    */
}