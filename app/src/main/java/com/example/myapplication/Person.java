package com.example.myapplication;

public class Person {
    private String role;
    private String firstName;
    private String lastName;
    private String userName;
    private String Age;
    private String address;
    //for host
    public Person(String role,String firstName, String lastName, String userName, String age, String address){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.role = role;
        this.Age = age;
        this.address = address;
    }
    //for guest
    public Person(String role,String firstName, String lastName, String userName, String age){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.role = role;
        this.Age = age;
    }
    public Person(){}

    public String getRole(){return role;}

    public String getFirstName(){ return firstName;}

    public String getLastName(){ return lastName;}

    public String getUserName(){ return userName;}

    public String getAge() {
        return Age;
    }

    public String getAddress(){
        return address;
    }

    public String toString(){
        String str = "FirstName:"+firstName+" Lastname:"+lastName+"Role: "+role;
        return str;
    }
}
