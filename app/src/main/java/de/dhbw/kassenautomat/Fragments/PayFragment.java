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
public class PayFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO initialize fields before createView
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /**
         * Set the layout where you can pay a ticket with the given LayoutInflater.
         */
        View layoutView = inflater.inflate(R.layout.fragment_pay, null);

        // so it can be displayed
        return layoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
