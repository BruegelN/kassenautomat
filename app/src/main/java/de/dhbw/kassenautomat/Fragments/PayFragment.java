package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

// To access the XML layouts easily
import java.util.Map;

import de.dhbw.kassenautomat.COIN_DATA;
import de.dhbw.kassenautomat.Dialogs.CustomOkDialog;
import de.dhbw.kassenautomat.ParkingTicket;
import de.dhbw.kassenautomat.PaymentManager;
import de.dhbw.kassenautomat.R;

/**
 * Created by nicob on 21.04.16.
 */
public class PayFragment extends Fragment {
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
    private TextView lblMoneyLeftToPay;

    private OverviewFragment FragmentOverview;
    private OutputFragment FragmentOutput;

    private PaymentManager paymentmgr;

    // The most important feature
    private int easterEggCounter = 0;
    private static final int easterEggFunLevel = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO initialize fields before createView

        super.onCreate(savedInstanceState);
        FragmentOverview = (OverviewFragment) Fragment.instantiate(this.getActivity(), OverviewFragment.class.getName(), null);
        FragmentOutput = (OutputFragment) Fragment.instantiate(this.getActivity(), OutputFragment.class.getName(), null);
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

        lblMoneyLeftToPay = (TextView) layoutPay.findViewById(R.id.lblMoneyLeftToPay);

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


        // reset the image every time the fragment is created
        btnFiftyCent.setImageResource(R.drawable.fifty_cent);

        // unpack the selected ticket
        Bundle args = this.getArguments();
        ParkingTicket tmpTicket = (ParkingTicket) args.getSerializable(ParkingTicket.SERIAL_KEY);
        // and prepare it for further usage
        paymentmgr = new PaymentManager(tmpTicket);
        // set initial remainingPrice
        setRemainingPrice(paymentmgr.calculatePrice());

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
            undoPayment();

            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentOverview)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    };



    private View.OnClickListener btnFiveCentPressed = new View.OnClickListener() {
        public void onClick(View v) {
            insertCoin(5);
        }
    };
    private View.OnClickListener btnTenCentPressed = new View.OnClickListener() {
        public void onClick(View v) {
            insertCoin(10);
        }
    };
    private View.OnClickListener btnTwentyCentPressed = new View.OnClickListener() {
        public void onClick(View v) {
            insertCoin(20);
        }
    };
    private View.OnClickListener btnFiftyCentPressed = new View.OnClickListener() {
        public void onClick(View v) {
            easterEggCounter++;
            if(easterEggCounter >= easterEggFunLevel)
            {
                btnFiftyCent.setImageResource(R.drawable.fifty_cent_fun);
            }

            if (easterEggCounter >= 5)
            {
                //
            }
            insertCoin(50);

        }
    };
    private View.OnClickListener btnOneEuroPressed = new View.OnClickListener() {
        public void onClick(View v) {
            insertCoin(100);
        }
    };
    private View.OnClickListener btnTwoEuroPressed = new View.OnClickListener() {
        public void onClick(View v) {
            insertCoin(200);
        }
    };

    private void insertCoin(int coinValue)
    {
        btnAbort.setEnabled(false);

        String message;
        int result;

        switch (result = paymentmgr.insertCoin(coinValue))
        {
            case 2:
            {
                message = String.format("Zahlung abgeschlossen.");
                break;
            }

            case 0:
            {
                message = String.format("Es ist keine weitere Geldeingabe erforderlich.");
                break;
            }

            case 1:
            {
                // TODO maybe sound of a coin
                message = String.format("%,.2f € bezahlt", (float)coinValue/(float)100);
                break;
            }

            case -1:
            {
                message = String.format("Der Automat kann keine weiteren %,.2f € Münzen annehmen, da der Münzspeicher voll ist.", (float)coinValue/(float)100);
                break;
            }

            case -2:
            {
                // TODO maybe Durchfallgeräusche
                message = String.format("Die Münze konnte nicht erkannt werden...\nVersuchen Sie es erneut!");
                break;
            }
            default:
            {
                message = String.format("Unerwarter Rückgabewert. Wenden Sie sich an den Administrator!");
            }
        }

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        float remainingPrice = paymentmgr.calculatePrice();
        setRemainingPrice(remainingPrice);

        if (result == 2)
        {
            btnAbort.setEnabled(false);
            Map<Integer, Integer> change = paymentmgr.getChange((int)(remainingPrice*100));
            dropChange(change);

            Bundle paymentData = new Bundle();
            paymentData.putSerializable(PaymentManager.SERIAL_KEY, paymentmgr);

            FragmentOutput.setArguments(paymentData);

            // change to view Output where the Parking coin is displayed and receipt stuff can be handled.
            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentOutput)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
        else if (result != 0)
        {
            btnAbort.setEnabled(true);
        }
    }

    private void undoPayment()
    {
        Map<Integer, Integer> changeMoney = paymentmgr.undoPayment();
        dropChange(changeMoney);
    }

    private void dropChange(Map<Integer, Integer> change)
    {
        String message;
        String sChange = "";

        if (change != null)
        for (int coin:COIN_DATA.COINS)
        {
            int amount = change.get(coin);
            if (amount>0)
                sChange += String.format("%d Münze(n) á %,.2f €\n", amount, (float)coin/(float)100);
        }

        if (!sChange.equals("")) {
            message = String.format("Bitte entnehmen Sie Ihr Rückgeld aus dem Ausgabefach:\n%s", sChange);

            // TODO maybe Geräusch?
            CustomOkDialog changeMoneyDialog = new CustomOkDialog();
            Bundle args = new Bundle();
            String title = getActivity().getApplicationContext().getString(R.string.dropChange);
            args.putString("title", title);
            args.putString("message", message);
            changeMoneyDialog.setArguments(args);
            changeMoneyDialog.setTargetFragment(this, 0);
            changeMoneyDialog.show(getFragmentManager(), "UniqueTagForAndroidToIdentifyReturnMoneyDialog");
        }
    }

    private  void setRemainingPrice(float remainingPrice)
    {
        String sRemaingPrice = String.format("%,.2f €", remainingPrice);
        lblMoneyLeftToPay.setText(sRemaingPrice);
    }
}
