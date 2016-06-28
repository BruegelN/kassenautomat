package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


import java.util.Calendar;
import java.util.Date;

import de.dhbw.kassenautomat.Dialogs.CustomOkDialog;
import de.dhbw.kassenautomat.MainActivity;
import de.dhbw.kassenautomat.ParkingTicket;
import de.dhbw.kassenautomat.R;

/**
 * Created by nicob on 13.05.16.
 */
public class TestTicketFragment extends Fragment {

    private TimePicker timePicker;
    private DatePicker datePicker;
    private TextView textView;

    private Button btnCancel;
    private Button btnTestTicket;

    private MaintenanceFragment FragmentMaintenance;

    private int mHourOfDay;
    private int mMinute;
    private int mYear ;
    private int mMonth; // NOTE: dayOfMonth starts with 0 so if we want to print it we have to +1!
    private int mDayOfMonth;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // the member as soon as possible
        mHourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        mMinute = Calendar.getInstance().get(Calendar.MINUTE);
        mYear = Calendar.getInstance().get(Calendar.YEAR);
        mMonth = Calendar.getInstance().get(Calendar.MONTH);
        mDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        FragmentMaintenance = (MaintenanceFragment) Fragment.instantiate(this.getActivity(), MaintenanceFragment.class.getName(), null);

        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Set the layout to create a new ticket for testing purpose with the given LayoutInflater.
         */
        View testTicketLayout = inflater.inflate(R.layout.fragment_test_ticket, container,false);

        btnCancel = (Button) testTicketLayout.findViewById(R.id.btnCancelTestTicket);
        btnTestTicket = (Button) testTicketLayout.findViewById(R.id.btnCreateTestTicket);

        btnCancel.setOnClickListener(btnCancelTestTicketPressed);
        btnTestTicket.setOnClickListener(btnCreateTestTicketPressed);


        timePicker = (TimePicker) testTicketLayout.findViewById(R.id.timePickerStartTestTicket);
        timePicker.setIs24HourView(true);

        textView = (TextView) testTicketLayout.findViewById(R.id.txtOverviewTestTicket);

        datePicker = (DatePicker) testTicketLayout.findViewById(R.id.datePickerTestTicket);
        datePicker.setMaxDate(new Date().getTime());


        datePicker.init(Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                        dateChanged
                        );

        timePicker.setOnTimeChangedListener(timeChanged);

        initTextForFakeLCD();
        // so it can be displayed
        return testTicketLayout;
    }

    private void initTextForFakeLCD()
    {
        // because all fields are initialized correctly it's enough to update the LCD
        updateFakeLCD();
    }

    private void updateFakeLCD(){
        textView.setText(getResources().getString(R.string.strCreateTestTicket));
        textView.append("\n ");
        if(mHourOfDay< 10)
        {
            textView.append("0");
        }
        textView.append(Integer.toString(mHourOfDay));

        textView.append(":");
        if(mMinute< 10)
        {
            textView.append("0");
        }
        textView.append(Integer.toString(mMinute));

        textView.append(" ");
        if(mDayOfMonth< 10)
        {
            textView.append("0");
        }
        textView.append(Integer.toString(mDayOfMonth));

        textView.append(".");
        if(mMonth< 10)
        {
            textView.append("0");
        }
        textView.append(Integer.toString(mMonth+1));

        textView.append(".");
        textView.append(Integer.toString(mYear));
    }

    private boolean isToday()
    {
        boolean isToday = (mYear == Calendar.getInstance().get(Calendar.YEAR)) && (mMonth== Calendar.getInstance().get(Calendar.MONTH)) && (mDayOfMonth == Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return isToday;
    }

    private boolean isLaterThanNow(int hourOfDay, int minute)
    {
        int hoursNow = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minutesNow = Calendar.getInstance().get(Calendar.MINUTE);

        if (hoursNow < hourOfDay || hoursNow == hourOfDay && minutesNow < minute) {
            return true;
        }
        else
        {
            return false;
        }
    }

    DatePicker.OnDateChangedListener dateChanged = new  DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker arg0, int year, int month, int dayOfMonth) {
            // we set the max. date in DatePicker.init to today so we don't have to check for it here

            mYear = year;
            mMonth = month; // somehow months start with 0
            mDayOfMonth = dayOfMonth;

            updateFakeLCD();
        }
    };


    TimePicker.OnTimeChangedListener timeChanged = new TimePicker.OnTimeChangedListener() {

        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

            mHourOfDay = hourOfDay;
            mMinute = minute;
            updateFakeLCD();
        }
    };


        View.OnClickListener btnCancelTestTicketPressed = new View.OnClickListener() {
        public void onClick(View v) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentMaintenance)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    };

    View.OnClickListener btnCreateTestTicketPressed = new View.OnClickListener() {
        public void onClick(View v) {


            if(isToday() && isLaterThanNow(mHourOfDay,mMinute))
            {
                int hourNow = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int minuteNow = Calendar.getInstance().get(Calendar.MINUTE);
                timePicker.setCurrentHour(hourNow);
                timePicker.setCurrentMinute(minuteNow);
                mHourOfDay = hourNow;
                mMinute = minuteNow;

                //Toast.makeText(getActivity(), R.string.strWarningTicketInFuture,Toast.LENGTH_LONG).show();
                CustomOkDialog changeMoneyDialog = new CustomOkDialog();
                Bundle args = new Bundle();
                args.putString("title", "Hinweis");
                args.putString("message", "Der gewählte Zeitpunk liegt in der Zukunft, aktueller Zeitpunkt verwendet!");
                changeMoneyDialog.setArguments(args);
                changeMoneyDialog.setTargetFragment(FragmentMaintenance, 0);
                changeMoneyDialog.show(getFragmentManager(), "UniqueTagForAndroidToIdentifyThisInfoDialog");
            }

            // set hours and minutes(HH:MM)
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MINUTE, mMinute);
            cal.set(Calendar.HOUR_OF_DAY, mHourOfDay);
            // set day, month and year (DD.MM.YYYY)
            cal.set(Calendar.DAY_OF_MONTH, mDayOfMonth-1); // first month is '0'
            cal.set(Calendar.MONTH, mMonth);
            cal.set(Calendar.YEAR, mYear);

            // create a new date object with the selected values.
            Date theTime = new Date(cal.getTimeInMillis());

            // Then create ticket with the give date
            ParkingTicket testTicket = new ParkingTicket(theTime);

            // and save this ticket in DB
            MainActivity.getDBmanager().saveTicket(testTicket);

            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentMaintenance)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    };



}

