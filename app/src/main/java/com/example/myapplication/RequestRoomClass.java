package com.example.myapplication;

public class RequestRoomClass {
    String uid,roomid,  no, monthS, dateS, monthE, dateE, smoking;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public RequestRoomClass(String uid, String roomid, String no, String monthS, String dateS, String monthE, String dateE, String smoking) {
        this.uid = uid;
        this.roomid = roomid;
        this.no = no;
        this.monthS = monthS;
        this.monthE = monthE;
        this.dateS = dateS;
        this.dateE = dateE;
        this.smoking = smoking;
    }

    public RequestRoomClass() {
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getMonthS() {
        return monthS;
    }

    public void setMonthS(String monthS) {
        this.monthS = monthS;
    }

    public String getDateS() {
        return dateS;
    }

    public void setDateS(String dateS) {
        this.dateS = dateS;
    }

    public String getMonthE() {
        return monthE;
    }

    public void setMonthE(String monthE) {
        this.monthE = monthE;
    }

    public String getDateE() {
        return dateE;
    }

    public void setDateE(String dateE) {
        this.dateE = dateE;
    }
}
