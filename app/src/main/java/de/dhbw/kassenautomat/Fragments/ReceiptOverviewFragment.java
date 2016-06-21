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
import android.widget.ListView;

import java.util.ArrayList;

import de.dhbw.kassenautomat.MainActivity;
import de.dhbw.kassenautomat.ParkingTicket;
import de.dhbw.kassenautomat.R;
import de.dhbw.kassenautomat.Receipt;

/**
 * Created by trugf on 17.06.2016.
 */

public class ReceiptOverviewFragment extends ListFragment {

    private Button btnBack;

    private OverviewFragment FragmentOverview;
    private ReceiptDetailsFragment FragmentReceiptDetails;

    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<Integer> ReceiptIDs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        FragmentOverview = (OverviewFragment) Fragment.instantiate(this.getActivity(), OverviewFragment.class.getName(), null);
        FragmentReceiptDetails = (ReceiptDetailsFragment) Fragment.instantiate(this.getActivity(), ReceiptDetailsFragment.class.getName(), null);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View LayoutOverview = inflater.inflate(R.layout.fragment_receipt_overview, container, false);

        btnBack =  (Button) LayoutOverview.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(btnBackPressed);

        arrayList = FillList();

        arrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, arrayList);
        setListAdapter(arrayAdapter);

        return LayoutOverview;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Bundle ReceiptData = new Bundle();
        // Because the tickets have the same order in the list as well as in the DB
        // Access the [position]'s element is the ticket we want!
        ReceiptData.putSerializable("IntegerTicketID", ReceiptIDs.get(position));

        FragmentReceiptDetails.setArguments(ReceiptData);

        getFragmentManager().beginTransaction()
                .replace(R.id.mainFragmentContainer, FragmentReceiptDetails)
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }

    private View.OnClickListener btnBackPressed = new View.OnClickListener() {
        public void onClick(View v) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentOverview)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();

        }
    };

    private ArrayList<String> FillList()
    {
        ArrayList<String> result = new ArrayList<String>();
        ReceiptIDs = MainActivity.getDBmanager().getReceiptIDs();

        for (Integer id : ReceiptIDs)
        {
            result.add(String.format("Quittung für Ticket #%05d", id));
        }

        return result;
    }
}
