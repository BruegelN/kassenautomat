package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.dhbw.kassenautomat.MainActivity;
import de.dhbw.kassenautomat.ParkingTicket;
import de.dhbw.kassenautomat.R;
import de.dhbw.kassenautomat.TicketManager;

/**
 * Created by nicob on 21.04.16.
 */
public class OverviewFragment extends ListFragment{

    // Locally used to switch to these fragments.
    private MaintenanceFragment FragmentMaintenance;
    private PayFragment FragmentPay;
    private NewTicketFragment FragmentNewTicket;

    /**
     * Button to go in maintenance mode.
     * Private button for elements used in overview fragment.
     */
    private Button btnMaintenance;

    /**
     * Button to go pay a ticket.
     * Private button for elements used in overview fragment.
     */
    private Button btnPayTicket;

    /**
     * Button to create a new Ticket.
     * Private button for elements used in overview fragment.
     */
    private Button btnCreateTicket;

    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        /* TODO initialize missing fields before createView
            and add elements form database to list
         */

        /**
         * Instantiate needed Fragments with activity context.
         */
        FragmentMaintenance = (MaintenanceFragment) Fragment.instantiate(this.getActivity(), MaintenanceFragment.class.getName(), null);
        FragmentPay = (PayFragment) Fragment.instantiate(this.getActivity(), PayFragment.class.getName(), null);
        FragmentNewTicket = (NewTicketFragment) Fragment.instantiate(this.getActivity(), NewTicketFragment.class.getName(), null);

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /**
         * Set the overview layout with the given LayoutInflater.
         */
        View LayoutOverview = inflater.inflate(R.layout.fragment_overview, container, false);

        /**
         * Connect the buttons the corresponding view elements in the layout.
         */
        btnMaintenance = (Button) LayoutOverview.findViewById(R.id.btnMaintenance);
        btnCreateTicket = (Button) LayoutOverview.findViewById(R.id.btnStartNewTicketProcess);
        btnPayTicket = (Button) LayoutOverview.findViewById(R.id.btnEditTicket);


        // Set handlers for button click events.
        btnMaintenance.setOnClickListener(btnMaintenancePressed);
        btnCreateTicket.setOnClickListener(btnCreateTicketPressed);
        btnPayTicket.setOnClickListener(btnPayTicketPressed);

        // get values from database and display them
        arrayList = fillTheList();

        arrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, arrayList);
        setListAdapter(arrayAdapter);

        // so it can be displayed
        return LayoutOverview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * onClickListener for maintenance button.
     * Will switch the to maintenance fragment.
     */
    View.OnClickListener btnMaintenancePressed = new View.OnClickListener() {
        public void onClick(View v) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentMaintenance)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    };

    /**
     * onClickListener to create a new ticket
     */
    private View.OnClickListener btnCreateTicketPressed = new View.OnClickListener() {
        public void onClick(View v) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentNewTicket)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();

        }
    };


    /**
     * onClickListener for payTicket button.
     * Will switch to fragment where you can pay the ticket.
     */
    private View.OnClickListener btnPayTicketPressed = new View.OnClickListener() {
        public void onClick(View v) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentPay)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    };


    /**
     * Returns a new ArrayList<String> containing some values.
     * Later values fetched from database.
     */
    private ArrayList<String> fillTheList()
    {
        TicketManager tmr = MainActivity.getTicketMgr();
        List<ParkingTicket> tickets = tmr.getTicketList();

        ArrayList<String> sTickets = new ArrayList<String>();

        for (ParkingTicket ticket:tickets)
        {
            sTickets.add(String.format("#%05d  |  %s", ticket.getID(), ParkingTicket.getSimpleDateFormat().format(ticket.getCreated())));
        }

        return sTickets;
    }
}
