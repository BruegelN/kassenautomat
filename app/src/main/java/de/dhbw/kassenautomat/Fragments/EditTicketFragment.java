package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.dhbw.kassenautomat.R;

/**
 * Created by nicob on 21.04.16.
 */
public class EditTicketFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Set the maintenance layout with the given LayoutInflater.
         */
        View editTicket = inflater.inflate(R.layout.fragment_edit_ticket, container,false);

        // so it can be displayed
        return editTicket;
    }

}
