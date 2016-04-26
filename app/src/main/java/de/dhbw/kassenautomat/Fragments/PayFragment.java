package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

// To access the XML layouts easily
import de.dhbw.kassenautomat.R;

/**
 * Created by nicob on 21.04.16.
 */
public class PayFragment extends Fragment {

    // TODO Need a data structure to save already payed money and calculate return money!

    // Regular buttons
    private Button btnQuittung;
    private Button btnAbort;

    // Buttons for coins
    private ImageButton btnFiveCent;
    private ImageButton btnTenCent;
    private ImageButton btnTwentyCent;
    private ImageButton btnFiftyCent;
    private ImageButton btnOneEuro;
    private ImageButton btnTwoEuro;

    private OverviewFragment FragmentOverview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO initialize fields before createView
        super.onCreate(savedInstanceState);

        FragmentOverview = (OverviewFragment) Fragment.instantiate(this.getActivity(), OverviewFragment.class.getName(), null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /**
         * Set the layout where you can pay a ticket with the given LayoutInflater.
         */
        View layoutPay = inflater.inflate(R.layout.fragment_pay, container, false);

        /**
         * Bind buttons to corresponding view element identified by their ID.
         * And register button-click handlers.
         */
        btnQuittung = (Button) layoutPay.findViewById(R.id.btnQuittung);
        btnAbort = (Button) layoutPay.findViewById(R.id.btnAbort);
        btnQuittung.setOnClickListener(btnQuittungPressed);
        btnAbort.setOnClickListener(btnAbortPressed);

        /**
         * Wire money-buttons to UI elements.
         */
        btnFiveCent = (ImageButton) layoutPay.findViewById(R.id.btnFiveCent);
        btnTenCent = (ImageButton) layoutPay.findViewById(R.id.btnTenCent);
        btnTwentyCent = (ImageButton) layoutPay.findViewById(R.id.btnTwentyCent);
        btnFiftyCent = (ImageButton) layoutPay.findViewById(R.id.btnFiftyCent);
        btnOneEuro = (ImageButton) layoutPay.findViewById(R.id.btnOneEuro);
        btnTwoEuro = (ImageButton) layoutPay.findViewById(R.id.btnTwoEuro);

        /**
         * Register onClick handlers for money button.
         * Those are called when someone "pays" a amount of money.
         */
        btnFiveCent.setOnClickListener(btnFiveCentPressed);
        btnTenCent.setOnClickListener(btnTenCentPressed);
        btnTwentyCent.setOnClickListener(btnTwentyCentPressed);
        btnFiftyCent.setOnClickListener(btnFiftyCentPressed);
        btnOneEuro.setOnClickListener(btnOneEuroPressed);
        btnTwoEuro.setOnClickListener(btnTwoEuroPressed);


        // so it can be displayed
        return layoutPay;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * onClickListener to get the bill after payed ticket!
     *
     */
    private View.OnClickListener btnQuittungPressed = new View.OnClickListener() {
        public void onClick(View v) {

            // TODO check already enough payed!
            Toast.makeText(getActivity(), "TODO Quittung", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * onClickListener to abort pay ticket process.
     *
     */
    private View.OnClickListener btnAbortPressed = new View.OnClickListener() {
        public void onClick(View v) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentOverview)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    };



    private View.OnClickListener btnFiveCentPressed = new View.OnClickListener() {
        public void onClick(View v) {
            // TODO reduce amount of money to pay
            Toast.makeText(getActivity(), "5 ct bezahlt", Toast.LENGTH_SHORT).show();
        }
    };
    private View.OnClickListener btnTenCentPressed = new View.OnClickListener() {
        public void onClick(View v) {
            // TODO reduce amount of money to pay
            Toast.makeText(getActivity(), "10 ct bezahlt", Toast.LENGTH_SHORT).show();        }
    };
    private View.OnClickListener btnTwentyCentPressed = new View.OnClickListener() {
        public void onClick(View v) {
            // TODO reduce amount of money to pay
            Toast.makeText(getActivity(), "20 ct bezahlt", Toast.LENGTH_SHORT).show();
        }
    };
    private View.OnClickListener btnFiftyCentPressed = new View.OnClickListener() {
        public void onClick(View v) {
            // TODO reduce amount of money to pay
            Toast.makeText(getActivity(), "50 ct bezahlt", Toast.LENGTH_SHORT).show();
        }
    };
    private View.OnClickListener btnOneEuroPressed = new View.OnClickListener() {
        public void onClick(View v) {
            // TODO reduce amount of money to pay
            Toast.makeText(getActivity(), "1 € bezahlt", Toast.LENGTH_SHORT).show();
        }
    };
    private View.OnClickListener btnTwoEuroPressed = new View.OnClickListener() {
        public void onClick(View v) {
            // TODO reduce amount of money to pay
            Toast.makeText(getActivity(), "2 € bezahlt", Toast.LENGTH_SHORT).show();
        }
    };

}
