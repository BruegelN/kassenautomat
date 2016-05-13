package de.dhbw.kassenautomat;

import java.util.Date;

/**
 * Created by trugf on 03.05.2016.
 */
public class Receipt {
    private int FKid;
    private float ticketPrice;
    private float paidPrice;
    private float receivedChange;
    private Date paid;

    public Receipt(int FKid, float ticketPrice, float paidPrice, float receivedChange, Date paid)
    {
        this.FKid = FKid;

        this.ticketPrice = ticketPrice;
        this.paidPrice = paidPrice;
        this.receivedChange = receivedChange;

        this.paid = paid;
    }

    public int getFKid() {
        return FKid;
    }

    public float getTicketPrice() {
        return ticketPrice;
    }

    public Date getPaid() {
        return paid;
    }
}
