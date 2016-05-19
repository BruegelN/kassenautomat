package de.dhbw.kassenautomat.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;

import java.text.SimpleDateFormat;

import de.dhbw.kassenautomat.MainActivity;
import de.dhbw.kassenautomat.R;


/**
 * Created by trugf today
 */

public class CustomYesNoDialog extends DialogFragment {

    public static void ShowDialog(FragmentManager fmanager, Fragment targetFrag, CustomYesNoDialog YesNoDialog, String title, String message)
    {
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);

        YesNoDialog.setArguments(args);
        YesNoDialog.setTargetFragment(targetFrag, 0);
        YesNoDialog.show(fmanager, "UniqueTagForAndroidToIdentifyReturnMoneyDialog");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String title = args.getString("title", "");
        String message = args.getString("message", "");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doThingsForYes();
                    }
                })
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doThingsForNo();
                    }
                })
                .create();
    }

    protected void doThingsForYes()
    {

    }

    protected void doThingsForNo()
    {

    }
}