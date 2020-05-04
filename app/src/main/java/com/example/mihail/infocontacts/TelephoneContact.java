package com.example.mihail.infocontacts;


/**
 * Created by Mihail on 13/06/2017.
 */


public class TelephoneContact {
    private String imageRs;
    private String names;
    private String telNumber;



    public TelephoneContact() {

    }

    public TelephoneContact(String imageRs, String names, String telNumber) {
        this.imageRs = imageRs;
        this.names = names;
        this.telNumber = telNumber;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getImageRs() {
        return imageRs;
    }

    public void setImageRs(String imageRs) {
        this.imageRs = imageRs;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }






}
