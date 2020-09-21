package com.example.myapplication;

public class HotelProfile {
    String name ="",address="", city="",
    country="", owner="",
    phone="",email="",open="",closing="",checkin="", uid;

    public HotelProfile(String name, String address, String city, String country, String owner,
                        String phone, String email, String openHour, String openMin, String closingHour,
                        String closingMin, String checkinHour, String checkinMin, String uid) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.owner = owner;
        this.phone = phone;
        this.email = email;
        this.open = openHour + ":" + openMin;
        this.closing = closingHour + ":" + closingMin;
        this.checkin = checkinHour + ":" + checkinMin;
        this.uid = uid;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public HotelProfile(){}

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getOwner() {
        return owner;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String openH, String openM) {
        this.open = openH + ":" + openM;
    }

    public String getClosing() {
        return closing;
    }

    public void setClosing(String closingH, String closingM) {
        this.closing = closingH + ":" + closingM;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkinH, String checkinM) {
        this.checkin = checkinH + ":" + checkinM;
    }
}
