package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

// To access the XML layouts easily
import java.util.HashMap;
import java.util.Map;

import de.dhbw.kassenautomat.COIN_DATA;
import de.dhbw.kassenautomat.Database.DatabaseManager;
import de.dhbw.kassenautomat.MainActivity;
import de.dhbw.kassenautomat.R;

/**
 * Created by nicob on 21.04.16.
 */
public class MaintenanceFragment extends Fragment {

    private Button btnResetCoins;
    private Button btnEndMaintenance;

    Map<Integer, ProgressBar> pgr_Bars = new HashMap<Integer, ProgressBar>();
    Map<Integer, TextView> txt_Views = new HashMap<Integer, TextView>();

    private DatabaseManager dbm;

    private OverviewFragment FragmentOverview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /**
         * Instantiate fragment to return to overview.
         */
        FragmentOverview = (OverviewFragment) Fragment.instantiate(this.getActivity(), OverviewFragment.class.getName(), null);
        dbm = MainActivity.getDBmanager();

        /* TODO initialize fields before createView
           TODO read coin level from DB an set coresponding view elements
            and add elements to list form database
         */

        super.onCreate(savedInstanceState);
    }

    private void updateLevels() {
        for (int coin: COIN_DATA.COINS)
        {
            int coinLevel = dbm.getCoinLevel(coin);
            ProgressBar bar = pgr_Bars.get(coin);
            TextView txt = txt_Views.get(coin);

            bar.setProgress(coinLevel);
            txt.setText(Integer.toString(coinLevel) + "/" + Integer.toString(COIN_DATA.MAX_COIN_LVL));
        }
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

        pgr_Bars.put(0, (ProgressBar) layoutMaintenance.findViewById(R.id.prog0));
        pgr_Bars.put(5, (ProgressBar) layoutMaintenance.findViewById(R.id.prog5));
        pgr_Bars.put(10, (ProgressBar) layoutMaintenance.findViewById(R.id.prog10));
        pgr_Bars.put(20, (ProgressBar) layoutMaintenance.findViewById(R.id.prog20));
        pgr_Bars.put(50, (ProgressBar) layoutMaintenance.findViewById(R.id.prog50));
        pgr_Bars.put(100, (ProgressBar) layoutMaintenance.findViewById(R.id.prog100));
        pgr_Bars.put(200, (ProgressBar) layoutMaintenance.findViewById(R.id.prog200));

        txt_Views.put(0, (TextView) layoutMaintenance.findViewById(R.id.txt0Count));
        txt_Views.put(5,(TextView) layoutMaintenance.findViewById(R.id.txt5Count));
        txt_Views.put(10,(TextView) layoutMaintenance.findViewById(R.id.txt10Count));
        txt_Views.put(20,(TextView) layoutMaintenance.findViewById(R.id.txt20Count));
        txt_Views.put(50,(TextView) layoutMaintenance.findViewById(R.id.txt50Count));
        txt_Views.put(100,(TextView) layoutMaintenance.findViewById(R.id.txt100Count));
        txt_Views.put(200, (TextView) layoutMaintenance.findViewById(R.id.txt200Count));

        btnResetCoins.setOnClickListener(btnResetCoinsPressed);
        btnEndMaintenance.setOnClickListener(btnEndMaintenancePressed);

        updateLevels();
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
