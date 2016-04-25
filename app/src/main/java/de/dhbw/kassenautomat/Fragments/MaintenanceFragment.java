package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// To access the XML layouts easily
import de.dhbw.kassenautomat.R;

/**
 * Created by nicob on 21.04.16.
 */
public class MaintenanceFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /* TODO initialize fields before createView
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
        View layoutMaintenance = inflater.inflate(R.layout.fragement_maintenance, null);

        // so it can be displayed
        return layoutMaintenance;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
