package de.dhbw.kassenautomat.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import de.dhbw.kassenautomat.COIN_DATA;
import de.dhbw.kassenautomat.MainActivity;
import de.dhbw.kassenautomat.R;

/**
 * Created by trugf on 17.05.2016.
 */
public class EditSettingsFragment extends Fragment{

    private TextView txt_maxCoinLevel;
    private TextView txt_defaultCoinLevel;
    private TextView txt_PricePerHalfHour;
    private TextView txt_RejectedCoinsShare;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View layout_editSettings = inflater.inflate(R.layout.fragment_edit_settings, container, false);
        txt_maxCoinLevel = (TextView) layout_editSettings.findViewById(R.id.txt_maxCoinLevel);
        txt_defaultCoinLevel = (TextView) layout_editSettings.findViewById(R.id.txt_defaultCoinLevel);
        txt_PricePerHalfHour = (TextView) layout_editSettings.findViewById(R.id.txt_PricePerHalfHour);
        txt_RejectedCoinsShare = (TextView) layout_editSettings.findViewById(R.id.txt_RejectedCoinsShare);


        updateValues();

        return  layout_editSettings;
    }

    private void saveSettings()
    {
        //TODO use values from txt fields
        String message = COIN_DATA.setSettings(MainActivity.getDBmanager(), 300, 0.10f, 0.60f, 0.30f);

        if (message == "")
            message = "Einstellungen gespeichert";

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

        COIN_DATA.readConfig(MainActivity.getDBmanager());
    }

    private void updateValues() {
        txt_maxCoinLevel.setText(Integer.toString(COIN_DATA.MAX_COIN_LVL));
        txt_defaultCoinLevel.setText(Integer.toString((int)(COIN_DATA.DEFAULT_COIN_LEVEL*100)));
        txt_PricePerHalfHour.setText(String.format("%,.2f",COIN_DATA.COST_PER_HALF_HOUR / (float) 100));
        txt_RejectedCoinsShare.setText(Integer.toString((int)(COIN_DATA.REJECTED_COINS_SHARE*100)));
    }
}
