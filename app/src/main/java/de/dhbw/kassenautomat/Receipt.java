package de.dhbw.kassenautomat;

import java.util.Date;

/**
 * Created by trugf on 03.05.2016.
 */
public class Receipt {
    private int FKid;
    private float Price;
    private Date paid;

    public Receipt(int FKid, float Price, Date paid)
    {
        this.FKid = FKid;
        this.Price = Price;
        this.paid = paid;
    }

    public int getFKid() {
        return FKid;
    }

    public float getPrice() {
        return Price;
    }

    public Date getPaid() {
        return paid;
    }
}
