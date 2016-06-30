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

import org.w3c.dom.Text;

import de.dhbw.kassenautomat.Dialogs.CustomOkDialog;
import de.dhbw.kassenautomat.SETTINGS;
import de.dhbw.kassenautomat.Database.DatabaseManager;
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
    private TextView txt_passwordMaintenance;

    private Button btn_Save;
    private Button btn_Back;

    MaintenanceFragment FragmentMaintenance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentMaintenance = (MaintenanceFragment) Fragment.instantiate(this.getActivity(), MaintenanceFragment.class.getName(), null);
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
        txt_passwordMaintenance = (TextView) layout_editSettings.findViewById(R.id.txt_passwordMaintenance);


        btn_Save = (Button) layout_editSettings.findViewById(R.id.btn_SaveSettings);
        btn_Back = (Button) layout_editSettings.findViewById(R.id.btn_Abort);

        btn_Save.setOnClickListener(btnSavePressed);
        btn_Back.setOnClickListener(btnBackPressed);

        updateValues();

        return  layout_editSettings;
    }

    private View.OnClickListener btnBackPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goBackToMaintenance();
        }
    };

    private View.OnClickListener btnSavePressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveSettings();
            goBackToMaintenance();
        }
    };

    private void saveSettings()
    {
        DatabaseManager dbm = MainActivity.getDBmanager();

        int maxCoinLevel = Integer.parseInt(txt_maxCoinLevel.getText().toString());
        float defaultCoinLevel = Integer.parseInt(txt_defaultCoinLevel.getText().toString())/(float)100;
        float pricePerHalfHour = Float.parseFloat(txt_PricePerHalfHour.getText().toString());
        float rejectedCoinsShare = Integer.parseInt(txt_RejectedCoinsShare.getText().toString())/(float)100;
        String passwordMaintenance = txt_passwordMaintenance.getText().toString();

        //TODO ask user whether he is sure to reset coin levels after setting the new settings

        String message = SETTINGS.setSettings(dbm, maxCoinLevel, defaultCoinLevel, pricePerHalfHour, rejectedCoinsShare, passwordMaintenance);

        if (message == "")
            message = "Einstellungen gespeichert";

        CustomOkDialog settingsSavedDialog = new CustomOkDialog();
        Bundle args = new Bundle();
        args.putString("title", "Hinweis");
        args.putString("message", message);
        settingsSavedDialog.setArguments(args);
        settingsSavedDialog.setTargetFragment(this, 0);
        settingsSavedDialog.show(getFragmentManager(), "UniqueTagForAndroidToIdentifyThisDialogAsSettingsSaved");

        SETTINGS.readConfig(dbm);
        dbm.setDefaultCoinLevels();

        goBackToMaintenance();
    }

    private void updateValues() {
        txt_maxCoinLevel.setText(Integer.toString(SETTINGS.MAX_COIN_LVL));
        txt_defaultCoinLevel.setText(Integer.toString((int)(SETTINGS.DEFAULT_COIN_LEVEL*100)));
        txt_PricePerHalfHour.setText(Float.toString(SETTINGS.COST_PER_HALF_HOUR / (float) 100));
        txt_RejectedCoinsShare.setText(Integer.toString((int) (SETTINGS.REJECTED_COINS_SHARE * 100)));
        txt_passwordMaintenance.setText(SETTINGS.PASSWORD);
    }

    private void goBackToMaintenance()
    {
        getFragmentManager().beginTransaction()
                .replace(R.id.mainFragmentContainer, FragmentMaintenance)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
