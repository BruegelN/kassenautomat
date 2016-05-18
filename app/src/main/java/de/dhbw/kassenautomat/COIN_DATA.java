package de.dhbw.kassenautomat;

import org.mockito.internal.matchers.Null;

import de.dhbw.kassenautomat.Database.DatabaseManager;

/**
 * Created by trugf on 28.04.2016.
 */
public class COIN_DATA {
    public static final int[] COINS = {5, 10, 20, 50, 100, 200};
    public static final int PARKING_COIN = 0;

    // Default settings for the automaton, which can be set by maintenance worker
    private static String LBL_REJ_C_SHARE = "rejected_coins_share";
    public static float REJECTED_COINS_SHARE = 0.1f;

    private static String LBL_MAX_C_LVL = "maximum_coin_level";
    public static int MAX_COIN_LVL = 200;

    private static String LBL_COST_P_HH = "cost_per_half_hour";
    public static int COST_PER_HALF_HOUR = 50; // euro cents

    private static String LBL_DEF_COIN_LVL = "def_coin_level";
    public static float DEFAULT_COIN_LEVEL = 0.2f;

    public static void readConfig(DatabaseManager dbm)
    {
        String readValue =  dbm.read_setting(LBL_REJ_C_SHARE);
        if (readValue != null)
        {
            int valueAsInt = Integer.parseInt(readValue);
            if (0<=valueAsInt && valueAsInt<=100)
                REJECTED_COINS_SHARE = (float)valueAsInt/100;
        }

        readValue =  dbm.read_setting(LBL_MAX_C_LVL);
        if (readValue != null)
        {
            int valueAsInt = Integer.parseInt(readValue);
            if (0<valueAsInt)
                MAX_COIN_LVL = valueAsInt;
        }

        readValue =  dbm.read_setting(LBL_COST_P_HH);
        if (readValue != null)
        {
            int valueAsInt = Integer.parseInt(readValue);
            if (COINS[0]<=valueAsInt && valueAsInt%COINS[0]==0) //must be at least the lowest coin value and dividable by it
                COST_PER_HALF_HOUR = valueAsInt;
        }

        readValue =  dbm.read_setting(LBL_DEF_COIN_LVL);
        if (readValue != null)
        {
            int valueAsInt = Integer.parseInt(readValue);
            if (0<valueAsInt && valueAsInt<100) //must be at least the lowest coin value and dividable by it
                DEFAULT_COIN_LEVEL = (float)valueAsInt/100;
        }
    }

    public static String setSettings(DatabaseManager dbm, int maxCoinLevel, float defaultCoinLevel, float costsPerHalfHour, float rejectedCoinsShare)
    {
        String message = "";
        if (1<maxCoinLevel)
            dbm.set_setting(LBL_MAX_C_LVL, Integer.toString(maxCoinLevel));
        else
            message+="Fehler: Die Größe der Münzbehälter konnte nicht gespeichert werden, da der Wert außerhalb des Wertebereichs (>1) liegt.\n";

        if (defaultCoinLevel*maxCoinLevel>=1 && defaultCoinLevel<1)
            dbm.set_setting(LBL_DEF_COIN_LVL, Integer.toString((int)(defaultCoinLevel*100)));
        else
        {
            message+="Fehler: Der Standard-Füllstand der Münzbehälter konnte nicht gespeichert werden, da der Wert außerhalb des Wertebereichs liegt.\n" +
                    "Dieser muss kleiner als 100% sein und zusammen mit der Größe der Münzbehälter zu einem Minimalfüllstand von 1 führen.\n";
            if (DEFAULT_COIN_LEVEL*maxCoinLevel < 1)
                dbm.set_setting(LBL_DEF_COIN_LVL, Integer.toString(99)); // set default coin level to 99% if it is too low for the newly set max coin level
        }


        if (COINS[0]<costsPerHalfHour*100 && (int)(costsPerHalfHour*100)%COINS[0]==0)
        //must be at least the lowest coin value and dividable by it
            dbm.set_setting(LBL_COST_P_HH, Integer.toString((int)(costsPerHalfHour*100)));
        else
            message+="Fehler: Der Halbstundenpreis konnte nicht gespeichert werden, da der Wert außerhalb des Wertebereichs liegt.\n" +
                    String.format("Stellen Sie sicher, dass der Wert durch den kleinsten Münbetrag %.2f € teilbar ist und diesen nicht unterschreitet.\n", COINS[0]/(float)100);

        if (0<=rejectedCoinsShare && rejectedCoinsShare<=1)
            dbm.set_setting(LBL_REJ_C_SHARE, Integer.toString((int)(rejectedCoinsShare*100)));
        else
            message+="Fehler: Die Durchfallquote konnte nicht gespeichert werden, da der Wert außerhalb des Wertebereichs liegt.\n" +
                    "Dieser muss zwischen 0 und 100% liegen.\n";

        if (message!= "")
            message+="Prüfen Sie ihre Eingaben!";

        return message;
    }
}
