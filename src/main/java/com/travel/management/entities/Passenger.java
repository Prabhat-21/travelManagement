package com.travel.management.entities;

import lombok.Data;

@Data
public class Passenger {
    private String id;
    private int passengerNo;
    private String name;
    private MembershipType membership;

    private double balance;

    public Passenger(String passengerId, int passengerNo, String name, MembershipType membership, double balance) {
        this.id = passengerId;
        this.passengerNo = passengerNo;
        this.name = name;
        this.membership = membership;
        this.balance=balance;
    }
}
