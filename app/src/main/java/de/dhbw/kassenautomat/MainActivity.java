package de.dhbw.kassenautomat;


import android.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import de.dhbw.kassenautomat.Database.DatabaseManager;
import de.dhbw.kassenautomat.Fragments.OverviewFragment;


public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;
    private static DatabaseManager DBmanager;
    private static TicketManager TicketMgr;
    private OverviewFragment FragmentOverview;
    private boolean doubleBackToExitPressedOnce = false;

    public MainActivity()
    {
        instance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create the objects before switching views
        DBmanager = new DatabaseManager(getContext());
        TicketMgr = new TicketManager();
        SETTINGS.readConfig(DBmanager);

        /**
         * Instantiate the overview fragment so it can be displayed.
         */
        FragmentOverview = (OverviewFragment) Fragment.instantiate(this, OverviewFragment.class.getName(), null);

        /**
         * Place the overview inside of MainActivity fragment container.
         */
        getFragmentManager().beginTransaction()
                            .replace(R.id.mainFragmentContainer, FragmentOverview)
                            .addToBackStack(null)
                            .commit();
    }

    public static Context getContext()
    {
        return instance.getApplicationContext();
    }

    public static DatabaseManager getDBmanager()
    {
        return DBmanager;
    }
    public static TicketManager getTicketMgr()
    {
        return TicketMgr;
    }

    /**
     * This global handler is called whenever the BACK-button is pressed.
     * To close the app via back button you have to press it twice within two seconds.
     * On the first press only a hint is given.
     */
    @Override
    public void onBackPressed() {
        //Checking for fragment count on backstack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,R.string.strPressBackAgainToExit, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }
}
