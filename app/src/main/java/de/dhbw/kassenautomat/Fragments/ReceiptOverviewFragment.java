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
 * Created by trugf on 17.06.2016.
 */

public class ReceiptOverviewFragment extends Fragment {

    private Button btnBack;

    private OverviewFragment FragmentOverview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        FragmentOverview = (OverviewFragment) Fragment.instantiate(this.getActivity(), OverviewFragment.class.getName(), null);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View LayoutOverview = inflater.inflate(R.layout.fragment_receipt_overview, container, false);

        btnBack =  (Button) LayoutOverview.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(btnBackPressed);

        /*TODO: Fill list with receipts from Database*/

        return LayoutOverview;
    }

    private View.OnClickListener btnBackPressed = new View.OnClickListener() {
        public void onClick(View v) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentOverview)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();

        }
    };
}
