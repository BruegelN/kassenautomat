package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.dhbw.kassenautomat.R;

/**
 * Created by nicob on 28.04.16.
 */
public class NewTicketFragment extends Fragment {



    private OverviewFragment FragmentOverview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View newTicketLayout = inflater.inflate(R.layout.fragment_new_ticket, container, false);

        return newTicketLayout;
    }
}
