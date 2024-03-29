package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import de.dhbw.kassenautomat.Dialogs.CustomOkDialog;
import de.dhbw.kassenautomat.SETTINGS;
import de.dhbw.kassenautomat.Database.DatabaseManager;
import de.dhbw.kassenautomat.MainActivity;
import de.dhbw.kassenautomat.R;

/**
 * Created by nicob on 21.04.16.
 */
public class MaintenanceFragment extends Fragment {

    private Button btnResetCoins;
    private Button btnEndMaintenance;
    private Button btnResetDB;
    private Button btnShowSettings;
    private Button btnCreateTestTicket;

    Map<Integer, ProgressBar> pgr_Bars = new HashMap<Integer, ProgressBar>();
    Map<Integer, TextView> txt_Views = new HashMap<Integer, TextView>();

    private DatabaseManager dbm;

    private OverviewFragment FragmentOverview;
    private TestTicketFragment FragmentTestTicket;
    private EditSettingsFragment FragmentEditSettings;
    private MaintenanceFragment FramgentMaintaince;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /**
         * Instantiate fragment to return to overview.
         */
        FragmentEditSettings = (EditSettingsFragment) Fragment.instantiate(this.getActivity(), EditSettingsFragment.class.getName(), null);
        FragmentOverview = (OverviewFragment) Fragment.instantiate(this.getActivity(), OverviewFragment.class.getName(), null);
        FragmentTestTicket = (TestTicketFragment) Fragment.instantiate(this.getActivity(), TestTicketFragment.class.getName(), null);
        FramgentMaintaince = (MaintenanceFragment) Fragment.instantiate(this.getActivity(), MaintenanceFragment.class.getName(), null);
        dbm = MainActivity.getDBmanager();

        super.onCreate(savedInstanceState);
    }

    private void updateLevels() {
        for (int coin: SETTINGS.COINS)
        {
            int coinLevel = dbm.getCoinLevel(coin);
            ProgressBar bar = pgr_Bars.get(coin);
            TextView txt = txt_Views.get(coin);

            bar.setProgress(coinLevel);
            txt.setText(Integer.toString(coinLevel) + "/" + Integer.toString(SETTINGS.MAX_COIN_LVL));
        }

        // do the same for the Parking Coin
        int coinLevel = dbm.getCoinLevel(SETTINGS.PARKING_COIN);
        ProgressBar bar = pgr_Bars.get(SETTINGS.PARKING_COIN);
        TextView txt = txt_Views.get(SETTINGS.PARKING_COIN);

        bar.setProgress(coinLevel);
        txt.setText(Integer.toString(coinLevel) + "/" + Integer.toString(SETTINGS.MAX_COIN_LVL));
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
        btnResetDB = (Button) layoutMaintenance.findViewById(R.id.btnResetDB);
        btnShowSettings = (Button) layoutMaintenance.findViewById(R.id.btnShowSettings);
        btnCreateTestTicket = (Button) layoutMaintenance.findViewById(R.id.btnCreateTestTicket);

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
        btnResetDB.setOnClickListener(btnResetDbPressed);
        btnShowSettings.setOnClickListener(btnShowSettingsPressed);
        btnCreateTestTicket.setOnClickListener(btnCreateTestTicketPressed);

        updateLevels();
        // so it can be displayed
        return layoutMaintenance;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private View.OnClickListener btnResetDbPressed  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            askReset();
        }
    };

    void askReset()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               if (which == DialogInterface.BUTTON_POSITIVE) {
                   //Yes button clicked
                   doReset();
               }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder
                .setTitle(R.string.strTitelResetAutomata)
                .setMessage(R.string.strMessageResetAutomata)
                .setPositiveButton(android.R.string.yes, dialogClickListener)
                .setNegativeButton(android.R.string.no, dialogClickListener)
                .show();
    }

    void doReset()
    {
        SETTINGS.setDefaults();
        dbm.resetDatabase();

        updateLevels();
        String message = String.format("Der Automat wurde auf den Werkszustand zurückgesetzt.");
        CustomOkDialog dbResetDialog = new CustomOkDialog();
        Bundle args = new Bundle();
        args.putString("title", "Hinweis");
        args.putString("message", message);
        dbResetDialog.setArguments(args);
        dbResetDialog.setTargetFragment(FramgentMaintaince, 0);
        dbResetDialog.show(getFragmentManager(), "UniqueTagForAndroidToIdentifyThisDialogAsDBReset");

    }

    private View.OnClickListener btnShowSettingsPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO change view to Settings overview

            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentEditSettings)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    };

    private View.OnClickListener btnCreateTestTicketPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO change view to Create TestTicket
            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentTestTicket)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    };

    private View.OnClickListener btnResetCoinsPressed = new View.OnClickListener() {
        public void onClick(View v) {
            // reset coin levels to default
            dbm.setDefaultCoinLevels();
            updateLevels();

            // inform the service worker
            String message = String.format("Die Münzbehälter wurden geleert und mit je %d Münze(n) aufgefüllt.\n" +
                    "Die Parkmünzen wurden wieder auf den maximalen Füllstand %d aufgefüllt.",
                    (int)(SETTINGS.DEFAULT_COIN_LEVEL * SETTINGS.MAX_COIN_LVL), SETTINGS.MAX_COIN_LVL);

            CustomOkDialog coinsResetDialog = new CustomOkDialog();
            Bundle args = new Bundle();
            args.putString("title", "Hinweis");
            args.putString("message", message);
            coinsResetDialog.setArguments(args);
            coinsResetDialog.setTargetFragment(FramgentMaintaince, 0);
            coinsResetDialog.show(getFragmentManager(), "UniqueTagForAndroidToIdentifyThisDialogAsCoinsReset");


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
