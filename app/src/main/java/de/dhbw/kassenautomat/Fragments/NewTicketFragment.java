package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.dhbw.kassenautomat.COIN_DATA;
import de.dhbw.kassenautomat.MainActivity;
import de.dhbw.kassenautomat.R;
import de.dhbw.kassenautomat.TicketManager;

/**
 * Created by nicob on 28.04.16.
 */
public class NewTicketFragment extends Fragment {


    private OverviewFragment FragmentOverview;

    private Button btnCancelNewTicket;
    private Button btnCreateTicket;

    private TextView tvPrivePerUnit;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        FragmentOverview = (OverviewFragment)Fragment.instantiate(this.getActivity(), OverviewFragment.class.getName(), null);

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View newTicketLayout = inflater.inflate(R.layout.fragment_new_ticket, container, false);

        btnCreateTicket = (Button) newTicketLayout.findViewById(R.id.btnCreateTicket);
        btnCancelNewTicket = (Button) newTicketLayout.findViewById(R.id.btnCancelNewTicket);

        btnCreateTicket.setOnClickListener(btnCreateTicketPressed);
        btnCancelNewTicket.setOnClickListener(btnCancelNewTicketPressed);

        tvPrivePerUnit = (TextView) newTicketLayout.findViewById(R.id.lblPricePerUnit);

        return newTicketLayout ;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvPrivePerUnit.setText(String.format("%,.2f €",COIN_DATA.COST_PER_HALF_HOUR/(float)100));
    }

    View.OnClickListener btnCreateTicketPressed = new View.OnClickListener() {
        public void onClick(View v) {

            MainActivity.getTicketMgr().createTicket();
            Toast.makeText(getActivity(), "Bitte entnehmen Sie das Ticket aus dem Ausgabefach.", Toast.LENGTH_SHORT).show();
            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentOverview)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    };

    View.OnClickListener btnCancelNewTicketPressed = new View.OnClickListener() {
        public void onClick(View v) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentOverview)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    };

}
