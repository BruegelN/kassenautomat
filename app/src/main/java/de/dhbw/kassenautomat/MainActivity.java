package de.dhbw.kassenautomat;


import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.dhbw.kassenautomat.Fragments.OverviewFragment;


public class MainActivity extends AppCompatActivity {


    private OverviewFragment FragmentOverview;


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

    }
}
