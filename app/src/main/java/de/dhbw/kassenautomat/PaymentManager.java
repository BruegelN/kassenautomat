package de.dhbw.kassenautomat;

import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import de.dhbw.kassenautomat.Database.DatabaseManager;

/**
 * Created by trugf on 10.05.2016.
 */
public class PaymentManager {
    private ParkingTicket ticket;
    private Map<Integer,Integer> paidCoins;
    private DatabaseManager dbm;

    /**
     * Constructor
     * @param ticket ParkingTicket to work on.
     */
    public PaymentManager(ParkingTicket ticket)
    {
        dbm = MainActivity.getDBmanager();
        this.ticket = ticket;
        paidCoins = new HashMap<>();

        // Initialisation of the paid coins with 0
        for (int coin:COIN_DATA.COINS)
        {
            paidCoins.put(coin, 0);
        }
    }

    /**
     * This will calculate the remaining price to pay for the given ParkingTicket.
     * @return remaining price as float
     */
    public float calculatePrice()
    {
        int rawPrice = calculateRawPrice();
        int remainingPrice = rawPrice;

        // decrement rawPrice by already paid coins to receive remainingPrice
        for (int coin:COIN_DATA.COINS)
        {
            remainingPrice = remainingPrice - coin * paidCoins.get(coin);
        }

        return (float)remainingPrice/(float)100;
    }

    /**
     * This will calculate the raw price for the given ParkingTicket
     * @return rawPrice: integer representing the raw price for this ticket in euro cents.
     */
    private int calculateRawPrice()
    {
        Date ticketCreated = ticket.getCreated();
        Date now = new Date();

        long diff = now.getTime()- ticketCreated.getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

        int startedHalfHours = (int)(minutes/30)+1;

        int rawPrice = startedHalfHours * COIN_DATA.COST_PER_HALF_HOUR;

        return rawPrice;
    }

    /**
     *
     * @param value The value of the coin in euro cents.
     * @return  2 if the inserted coin was the last one needed
     *          1 if coin successfully inserted
     *          0 if no more payment necessary
     *          -1 if coin could not be accepted due to full storage
     *          -2 if coin could not be recognized
     */
    public int insertCoin(int value)
    {
        String message;
        int result;

        int coinLevel = dbm.getCoinLevel(value);

        if (calculatePrice()<=0)
        {
            // no more payment necessary
            return 0;
        }

        if (!acceptCoin())
        {
            return -2;
        }

        if(coinLevel>= COIN_DATA.MAX_COIN_LVL)
        {
            // We can not accept more of this coins, since the storage is already full
            return -1;
        }

        // add one to already paid coins
        paidCoins.put(value, paidCoins.get(value)+1);

        // add the inserted coin to the coin level
        dbm.setCoinLevel(value, coinLevel+1);

        if (calculatePrice()<=0)
            return 2; // last coin inserted
        return 1; // coin successfully inserted

    }

    /**
     * This will undo the payment made so far.
     * @return Map<Integer, Integer> representing the change money
     */
    public Map<Integer, Integer> undoPayment()
    {
        for (int coin:COIN_DATA.COINS)
        {
            int lvlBefore = dbm.getCoinLevel(coin);
            int lvlToSet = lvlBefore-paidCoins.get(coin);

            // remove all those already paid coins from the db
            dbm.setCoinLevel(coin, lvlToSet);
        }

        return paidCoins;
    }

    /**
     * This will take a random number and tell us whether the automaton will accept the inserted coin
     * @return boolean: 1 = coin accepted; 0 = not accepted
     */
    private boolean acceptCoin()
    {
        Random rd = new Random();
        return rd.nextFloat()>=0.10f; //10% of coins wont be accepted #evilFace
    }


    /**
     * This will take the change coins out of the storage and
     * @return it as Map<Integer, Integer>; null represents no change.
     */
    public Map<Integer, Integer> getChange(float Price)
    {
        Map<Integer, Integer> change = new HashMap<Integer, Integer>();
        float remainingPrice = Price;

        if (remainingPrice>=0)
            return null;

        // initialize change with 0 foreach coin
        for (int coin:COIN_DATA.COINS)
        {
            change.put(coin, 0);
        }

        // kind of foreach the COIN-Array reverse (highest first)
        for (int i=COIN_DATA.COINS.length-1; i>=0; i--)
        {
            int coin = COIN_DATA.COINS[i];
            float coinValue = coin*0.01f;
            int coinLevel = dbm.getCoinLevel(coin);

            if (remainingPrice + coinValue <= 0
                    && coinLevel > 0)
            {
                // take coin
                dbm.setCoinLevel(coin, coinLevel-1);
                // add to remaining price
                remainingPrice+=coinValue;
                // add to change money
                change.put(coin, change.get(coin)+1);

                //this means, we retry the same coinValue once more
                i++;
            }
        }

        // if the change fits, return it
        if (remainingPrice == 0)
        {
            return change;
        }
        else
        {
            // return the so far calculated change to the storage
            for (int coin:COIN_DATA.COINS)
            {
                int level = dbm.getCoinLevel(coin);
                dbm.setCoinLevel(coin, level-change.get(coin));
            }

            // recursively try to calculate change with additional lowest coinValue
            return getChange(Price-COIN_DATA.COINS[0]*0.01f);
        }
    }

    /**
     * Calculates the sum of the given coins
     * @return sum as float
     */
    public static float getSum(Map<Integer, Integer> coins)
    {
        int sum = 0;

        for (int coin:COIN_DATA.COINS)
        {
            sum += coin*coins.get(coin);
        }

        return sum*0.01f;
    }
}
