package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

// To access the XML layouts easily
import de.dhbw.kassenautomat.R;

/**
 * Created by nicob on 21.04.16.
 */
public class MaintenanceFragment extends Fragment {

    private Button btnResetCoins;
    private Button btnEndMaintenance;

    private OverviewFragment FragmentOverview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /**
         * Instantiate fragment to return to overview.
         */
        FragmentOverview = (OverviewFragment) Fragment.instantiate(this.getActivity(), OverviewFragment.class.getName(), null);


        /* TODO initialize fields before createView
           TODO read coin level from DB an set coresponding view elements
            and add elements to list form database
         */
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /**
        * Set the maintenance layout with the given LayoutInflater.
        */
        View layoutMaintenance = inflater.inflate(R.layout.fragment_maintenance, container, false);

        btnResetCoins = (Button) layoutMaintenance.findViewById(R.id.btnResetCoins);
        btnEndMaintenance = (Button) layoutMaintenance.findViewById(R.id.btnEndMaintenance);

        btnResetCoins.setOnClickListener(btnResetCoinsPressed);
        btnEndMaintenance.setOnClickListener(btnEndMaintenancePressed);

        // so it can be displayed
        return layoutMaintenance;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private View.OnClickListener btnResetCoinsPressed = new View.OnClickListener() {
        public void onClick(View v) {

            // TODO reset coin level!
            Toast.makeText(getActivity(), "TODO Münzen auffüllen!", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener btnEndMaintenancePressed = new View.OnClickListener() {
        public void onClick(View v) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentOverview)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    };
}
