package com.example.myapplication;

public class Room {
    String  smoking, max, bedT, bedQ, bathroom, wifi, table, dryer, towel, avail, checkin, checkout, current;
    String roomnumber, useridBooked;
    String roomSize;

    public Room(){}

    public Room (String roomnumber, String roomSize ,String smoke, String max, String bedT, String bedQ, String bathroom, String wifi, String table, String dryer, String towel){
        this.roomnumber = roomnumber;
        this.useridBooked = "";
        this.roomSize = roomSize;
        this.smoking = smoke;
        this.max = max;
        this.bedT = bedT;
        this.bedQ = bedQ;
        this.bathroom = bathroom;
        this.wifi = wifi;
        this.table = table;
        this.dryer = dryer;
        this.towel = towel;
        avail = "true";
        checkin = "";
        checkout = "";
        current = "0";
    }

    public Room (String roomnumber, String roomSize ,String smoke, String max, String bedT, String bedQ,
                 String bathroom, String wifi, String table, String dryer, String towel, String avail, String checkin, String checkout,
                 String current){
        this.roomnumber = roomnumber;
        this.useridBooked = "";
        this.roomSize = roomSize;
        this.smoking = smoke;
        this.max = max;
        this.bedT = bedT;
        this.bedQ = bedQ;
        this.bathroom = bathroom;
        this.wifi = wifi;
        this.table = table;
        this.dryer = dryer;
        this.towel = towel;
        this.avail = avail;
        this.checkin = checkin;
        this.checkout = checkout;
        this.current = current;
    }

    public String getUseridBooked() {
        return useridBooked;
    }

    public void setUseridBooked(String useridBooked) {
        this.useridBooked = useridBooked;
    }

    public String getRoomnumber(){return roomnumber;}
    public String getRoomSize(){return roomSize;}
    public String getSmoking(){
        return smoking;
    }
    public String getMax(){
        return max;
    }
    public String getBedT(){
        return bedT;
    }
    public String getBedQ(){
        return bedQ;
    }
    public String getBathroom(){
        return bathroom;
    }
    public String getWifi(){
        return wifi;
    }
    public String getTable(){
        return table;
    }
    public String getDryer() {
        return dryer;
    }

    public void setRoomnumber(String roomnumber){this.roomnumber = roomnumber;}
    public void setRoomSize(String roomSize){this.roomSize = roomSize;}
    public void setSmoking(String smoking){this.smoking = smoking;}

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public void setMax(String max){this.max = max;}
    public void setBedT(String bedT){this.bedT = bedT;}
    public void setBedQ(String bedQ){this.bedQ = bedQ;}
    public void setBathroom(String bathroom){this.bathroom = bathroom;}
    public void setWifi(String wifi){this.wifi = wifi;}
    public void setTable(String table){this.table = table;}
    public void setDryer(String dryer){this.dryer = dryer;}
    public void setTowel(String towel){this.table =towel;}

    public String getTowel() {
        return towel;
    }

    public String getAvail() {
        return avail;
    }

    public void setAvail(String avail) {
        this.avail = avail;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin, String checkinmin) {
        this.checkin = checkin +"/" + checkinmin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout, String checkoutdate) {
        this.checkout = checkout +"/" + checkoutdate;
    }
}
