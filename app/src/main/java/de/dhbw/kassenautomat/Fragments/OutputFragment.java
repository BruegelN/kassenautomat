package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.dhbw.kassenautomat.PaymentManager;
import de.dhbw.kassenautomat.R;

/**
 * Created by nicob on 21.04.16.
 */
public class OutputFragment extends Fragment {

    private Button buttonOK;

    private OverviewFragment FragmentOverview;

    private PaymentManager paymentmgr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //TODO do stuff (maybe)

        FragmentOverview = (OverviewFragment)(Fragment.instantiate(this.getActivity(), OverviewFragment.class.getName(), null));
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View LayoutOutput = inflater.inflate(R.layout.fragment_output, container, false);

        buttonOK = (Button) LayoutOutput.findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(buttonOkPressed);

        Bundle arguments = this.getArguments();
        this.paymentmgr = (PaymentManager) arguments.getSerializable(PaymentManager.SERIAL_KEY);

        updatePaymentData();

        return LayoutOutput;
    }

    View.OnClickListener buttonOkPressed = new View.OnClickListener() {
        public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentOverview)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    };

    private void updatePaymentData()
    {
        
    }
}
