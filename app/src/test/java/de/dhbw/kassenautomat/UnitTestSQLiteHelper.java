package de.dhbw.kassenautomat;

import de.dhbw.kassenautomat.Database.DatabaseManager;
import de.dhbw.kassenautomat.Database.MySQLiteOpenHelper;
import de.dhbw.kassenautomat.TicketManager;

import static org.junit.Assert.*;
import android.test.mock.MockContext;
import android.test.InstrumentationTestCase;
import org.junit.Test;

public class UnitTestSQLiteHelper extends InstrumentationTestCase {

    private DatabaseManager dbm;
    private TicketManager tm;

    //@Before
    public void setUp() throws Exception {
        super.setUp();

        MockContext context = new MockContext();
        assertNotNull(context);
        dbm = new DatabaseManager(context);
        tm = new TicketManager();
    }

    //@After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    //@Test
    public void testDBreadNwrite() throws Exception {
        assertTrue(false);
        assertNotNull(null);
        assertNotNull(dbm.dbread);
        assertNotNull(dbm.dbwrite);
    }

    //@Test
    public void testSaveTicket() {
        ParkingTicket testTicket = tm.createTicket();

        assertTrue(testTicket != null && testTicket.getPrintQuality() >= 80 && testTicket.getCreated() != null
                && testTicket.getID() > -1);

        assertTrue(dbm.saveTicket(testTicket));

        assertTrue(dbm.getTickets().toString(), dbm.getTickets().size() == 1);

        assertTrue(dbm.getCoinLevel(5), dbm.getCoinLevel(5).equals("300"));
    }
}