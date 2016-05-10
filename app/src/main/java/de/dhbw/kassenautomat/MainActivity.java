package de.dhbw.kassenautomat;


import android.app.Fragment;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.dhbw.kassenautomat.Database.DatabaseManager;
import de.dhbw.kassenautomat.Fragments.OverviewFragment;


public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;
    private static DatabaseManager DBmanager;
    private OverviewFragment FragmentOverview;

    public MainActivity()
    {
        instance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        DBmanager = new DatabaseManager(getContext());
    }

    public static Context getContext()
    {
        return instance.getApplicationContext();
    }

    public static DatabaseManager getDBmanager()
    {
        return DBmanager;
    }
}
