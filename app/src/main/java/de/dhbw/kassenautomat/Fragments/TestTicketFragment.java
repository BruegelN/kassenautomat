package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

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

    private Button btnCancle;
    private Button btnTestTicket;

    private MaintenanceFragment FragmentMaintenance;

    private int mHourOfDay;
    private int mMinute;
    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    @Override
    public void onCreate(Bundle savedInstanceState) {

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

        btnCancle = (Button) testTicketLayout.findViewById(R.id.btnCancelTestTicket);
        btnTestTicket = (Button) testTicketLayout.findViewById(R.id.btnCreateTestTicket);

        btnCancle.setOnClickListener(btnCancelTestTicketPressed);
        btnTestTicket.setOnClickListener(btnCreateTestTicketPressed);


        timePicker = (TimePicker) testTicketLayout.findViewById(R.id.timePickerStartTestTicket);
        timePicker.setIs24HourView(true);

        textView = (TextView) testTicketLayout.findViewById(R.id.txtOverviewTestTicket);

        datePicker = (DatePicker) testTicketLayout.findViewById(R.id.datePickerTestTicket);
        datePicker.setMaxDate(new Date().getTime());


        datePicker.init(Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                        new DatePicker.OnDateChangedListener() {
                            @Override
                            public void onDateChanged(DatePicker arg0, int year, int month, int dayOfMonth) {
                                mYear = year;
                                mMonth = month+1; // somehow months start with 0
                                mDayOfMonth = dayOfMonth;

                                updateLCD();
                                }
                        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                int hoursNow = new Time(System.currentTimeMillis()).getHours();
                int minutesNow = new Time(System.currentTimeMillis()).getMinutes();

                if(isToday()) {
                    System.out.println("TODAY");
                    if (hoursNow <= hourOfDay && minutesNow <= minute) {
                        System.out.println("TODAY && future");
                        // the ticket should be created in the future which is impossible!
                        mHourOfDay = hoursNow;
                        mMinute = minutesNow;
                        timePicker.setCurrentHour(hoursNow);
                        timePicker.setCurrentMinute(minutesNow);
                        updateLCD();
                    }
                }
                else
                {
                    mHourOfDay = hourOfDay;
                    mMinute = minute;
                }

                updateLCD();
            }
        });

        updateLCD();
        // so it can be displayed
        return testTicketLayout;
    }

    private void initTextForFakeLCD()
    {


        updateLCD();
    }

    private void updateLCD(){
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
        textView.append(Integer.toString(mMonth));

        textView.append(".");
        textView.append(Integer.toString(mYear));
    }

    private boolean isToday()
    {

        return ((mYear == Calendar.getInstance().get(Calendar.YEAR)) && (mMonth== Calendar.getInstance().get(Calendar.MONTH)+1) && (mDayOfMonth == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
    }

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
            // set hours and minutes(HH:MM)
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MINUTE, mMinute);
            cal.set(Calendar.HOUR_OF_DAY, mHourOfDay);
            // set day, month and year (DD.MM.YYYY)
            cal.set(Calendar.DAY_OF_MONTH, mDayOfMonth);
            cal.set(Calendar.MONTH, mMonth);
            cal.set(Calendar.YEAR, mYear);
            // create a new date object with the selected values.
            Date theTime = new Date(cal.getTimeInMillis());
            Date now = new Date();
            if(now.before(theTime))
            {
                Toast.makeText(getActivity(), R.string.strWarningTicketInFuture,Toast.LENGTH_LONG).show();
            }
            // Then create ticket with the give date
            ParkingTicket testTicket = new ParkingTicket(theTime);
            Log.d("TAG","TheTime: "+theTime.toString());
            Log.d("TAG","Ticket created:"+testTicket.getCreated().toString());
            // and save this ticket in DB
            MainActivity.getDBmanager().saveTicket(testTicket);

            getFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, FragmentMaintenance)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    };



}

