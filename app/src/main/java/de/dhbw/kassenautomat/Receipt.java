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
    private int minutesParked;
    private Date paid;
    private Date ticket_created;

    public Receipt(int FKid, float ticketPrice, float paidPrice, float receivedChange, int minutesParked, Date paid)
    {
        this.FKid = FKid;

        this.ticketPrice = ticketPrice;
        this.paidPrice = paidPrice;
        this.receivedChange = receivedChange;

        this.minutesParked = minutesParked;

        this.paid = paid;
    }

    public int getFKid() {
        return FKid;
    }

    public float getTicketPrice() {
        return ticketPrice;
    }

    public float getPaidPrice() {
        return paidPrice;
    }

    public float getReceivedChange() {
        return receivedChange;
    }

    public int getMinutesParked() {
        return minutesParked;
    }

    public Date getPaid() {
        return paid;
    }

    public Date getTicket_created() {
        return ticket_created;
    }

    public void setTicket_created(Date ticket_created) {
        this.ticket_created = ticket_created;
    }
}
