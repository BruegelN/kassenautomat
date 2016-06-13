package de.dhbw.kassenautomat;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import java.lang.Exception;
import java.lang.String;
import java.util.Date;
import java.util.List;

import de.dhbw.kassenautomat.Database.DatabaseManager;
import de.dhbw.kassenautomat.Database.MySQLiteOpenHelper;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UnitTestDatabase {
    private final String dbName = MySQLiteOpenHelper.DB_NAME;
    private final int dbVersion = MySQLiteOpenHelper.VERSION;

    DatabaseManager dbm;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp() throws Exception {

        Context context = mActivityRule.getActivity().getApplication().getApplicationContext();
        assertNotNull(context);

        dbm = new DatabaseManager(context);
    }

    @After
    public void tearDown()
    {
        dbm.deleteDatabase();
    }

    @Test
    public void test_setCoinLevel() throws Exception
    {
        int cl5_before = dbm.getCoinLevel(5);
        dbm.setCoinLevel(5, cl5_before + 1);
        int cl5_after = dbm.getCoinLevel(5);

        assertTrue(cl5_after - 1 == cl5_before);
    }

    @Test
    public void test_saveTicket() throws Exception
    {
        int ticket_count_before = dbm.getUnpaidTickets().size();

        ParkingTicket Ticket = new ParkingTicket();
        assertTrue(dbm.saveTicket(Ticket));

        int ticket_count_after =  dbm.getUnpaidTickets().size();

        assertTrue(Integer.toString(ticket_count_after) + "<after before>" + Integer.toString(ticket_count_before),ticket_count_after-1 == ticket_count_before);
    }

    @Test //this will validate, that the dbm returns the correctly formatted strings needed to recreate a ParkingTicket instance.
    public void test_reinstantiateTicket() throws Exception
    {
        test_saveTicket();
        List<String> savedTickets = dbm.getUnpaidTickets();

        assertTrue(savedTickets.size() > 0); //There must be at least one saved ticket for this test. This should be given from the test above

        ParkingTicket pT = new ParkingTicket(savedTickets.get(0)); //this might throw an exeption

        System.out.println(pT.toString());
    }

    @Test
    public void test_removeTicket() throws Exception
    {
        //get some Ticket from the DB or create and save a new one if DB is empty
        ParkingTicket pT;
        if (dbm.getUnpaidTickets().size()>0)
        {
            pT = new ParkingTicket(dbm.getUnpaidTickets().get(0));
        }
        else
        {
            pT= new ParkingTicket();
            assertTrue(dbm.saveTicket(pT));
            pT = new ParkingTicket(dbm.getUnpaidTickets().get(0));
            //pT must be read from DB, since its ID is created by DB
        }

        assertNotNull(pT);

        int ticket_count_before = dbm.getUnpaidTickets().size();

        assertTrue(dbm.removeTicket(pT.getID()));

        int ticket_count_after = dbm.getUnpaidTickets().size();

        assertTrue(ticket_count_after + 1 == ticket_count_before); //some kind of double check, just to be sure
    }

    @Test
    public void test_setTicketPaidNgetReceipt() throws Exception
    {
        //get some Ticket from the DB or create and save a new one if DB is empty
        ParkingTicket pT;
        if (dbm.getUnpaidTickets().size()>0)
        {
            pT = new ParkingTicket(dbm.getUnpaidTickets().get(0));
        }
        else
        {
            pT= new ParkingTicket();
            assertTrue(dbm.saveTicket(pT));
            pT = new ParkingTicket(dbm.getUnpaidTickets().get(0));
            //pT must be read from DB, since its ID is created by DB
        }

        assertNotNull(pT);

        assertTrue(dbm.setTicketPaid(new Receipt(pT.getID(), 1.50f, 1.55f, 0.05f, 89, new Date()), true));

        Receipt rc = dbm.getReceipt(pT.getID());

        assertTrue(rc.getTicketPrice() == 1.50f);
        assertTrue(rc.getFKid() == pT.getID());
    }

    //TicketManager uses DB, so it's tested here, basically the functionality is the same as above
    @Test
    public void test_TicketManager() throws Exception
    {
        TicketManager tm = new TicketManager();

        int tickets_count_before = tm.getTicketList().size();

        //try to create a new ticket
        ParkingTicket pT = tm.createTicket();

        List<ParkingTicket> ticketList = tm.getTicketList();
        int tickets_count_after = ticketList.size();
        assertTrue(tickets_count_after-1 == tickets_count_before);

        //try to delete the first saved ticket
        assertTrue(tm.removeTicket(ticketList.get(0).getID()));
    }
}
