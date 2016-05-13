package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.dhbw.kassenautomat.Database.DatabaseManager;
import de.dhbw.kassenautomat.MainActivity;
import de.dhbw.kassenautomat.PaymentManager;
import de.dhbw.kassenautomat.R;
import de.dhbw.kassenautomat.Receipt;

/**
 * Created by nicob on 21.04.16.
 */
public class OutputFragment extends Fragment {

    private Button buttonOK;

    private OverviewFragment FragmentOverview;

    private DatabaseManager dbm;
    private PaymentManager paymentmgr;
    private Receipt tmpReceipt;


    private Button btnPrintReceipt;
    private TextView txtParkedTime;
    private TextView txtTicketPrice;
    private TextView txtPaidPrice;
    private TextView txtReceivedChange;

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

        btnPrintReceipt = (Button) LayoutOutput.findViewById(R.id.btnPrintReceipt);
        btnPrintReceipt.setOnClickListener(btnPrintReceiptPressed);

        txtParkedTime = (TextView) LayoutOutput.findViewById(R.id.txtParkedTime);
        txtTicketPrice = (TextView) LayoutOutput.findViewById(R.id.txtTicketPrice);
        txtPaidPrice = (TextView) LayoutOutput.findViewById(R.id.txtPaidPrice);
        txtReceivedChange = (TextView) LayoutOutput.findViewById(R.id.txtReceivedChange);

        Bundle arguments = this.getArguments();
        this.paymentmgr = (PaymentManager) arguments.getSerializable(PaymentManager.SERIAL_KEY);
        this.tmpReceipt = paymentmgr.getReceipt();

        dbm = MainActivity.getDBmanager();
        // set this ticket as paid without saving the receipt by default
        dbm.setTicketPaid(tmpReceipt, false);

        printPaymentData();

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

    View.OnClickListener btnPrintReceiptPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (dbm.setTicketPaid(tmpReceipt, true))
            {
                // TODO: show dialog to confirm that the receipt has been saved
                Toast.makeText(getActivity(), "Quittung gedruckt.", Toast.LENGTH_SHORT).show();
                //act as if OK was clicked to go back to the main menu
                buttonOkPressed.onClick(v);
            }
            else
            {
                Toast.makeText(getActivity(), "Fehler beim Drucken der Quittung. :(", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void printPaymentData()
    {
        txtParkedTime.setText(String.format("%d:%02d h",(int)(tmpReceipt.getMinutesParked() / 60), (tmpReceipt.getMinutesParked() % 60)));

        txtTicketPrice.setText(String.format("%,.2f €", tmpReceipt.getTicketPrice()));
        txtPaidPrice.setText(String.format("%,.2f €", tmpReceipt.getPaidPrice()));
        txtReceivedChange.setText(String.format("%,.2f €", tmpReceipt.getReceivedChange()));
    }
}
