package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.dhbw.kassenautomat.MainActivity;
import de.dhbw.kassenautomat.PaymentManager;
import de.dhbw.kassenautomat.R;
import de.dhbw.kassenautomat.Receipt;

/**
 * Created by trugf on 21.06.2016.
 */
public class ReceiptDetailsFragment extends Fragment {
    private ReceiptOverviewFragment FragmentReceiptOverview;
    private Integer TicketID;
    private Receipt receipt;

    private TextView txtParkedTime;
    private TextView txtTicketPrice;
    private TextView txtPaidPrice;
    private TextView txtReceivedChange;

    private Button btnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        FragmentReceiptOverview = (ReceiptOverviewFragment) Fragment.instantiate(this.getActivity(), ReceiptOverviewFragment.class.getName(), null);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View LayoutDetailsView = inflater.inflate(R.layout.fragment_receipt_details_view, container, false);

        txtParkedTime = (TextView) LayoutDetailsView.findViewById(R.id.txtParkedTime);
        txtTicketPrice = (TextView) LayoutDetailsView.findViewById(R.id.txtTicketPrice);
        txtPaidPrice = (TextView) LayoutDetailsView.findViewById(R.id.txtPaidPrice);
        txtReceivedChange = (TextView) LayoutDetailsView.findViewById(R.id.txtReceivedChange);

        btnBack =  (Button) LayoutDetailsView.findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(btnBackPressed);

        Bundle arguments = this.getArguments();
        this.TicketID = (Integer) arguments.getSerializable("IntegerTicketID");
        this.receipt = MainActivity.getDBmanager().getReceipt(this.TicketID);

        printPaymentData();

        return LayoutDetailsView;
    }


    private View.OnClickListener btnBackPressed = new View.OnClickListener() {
        public void onClick(View v) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentReceiptOverview)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();

        }
    };

    private void printPaymentData()
    {
        txtParkedTime.setText(String.format("%d:%02d h",(int)(receipt.getMinutesParked() / 60), (receipt.getMinutesParked() % 60)));

        txtTicketPrice.setText(String.format("%,.2f €", receipt.getTicketPrice()));
        txtPaidPrice.setText(String.format("%,.2f €", receipt.getPaidPrice()));
        txtReceivedChange.setText(String.format("%,.2f €", receipt.getReceivedChange()));
    }
}
