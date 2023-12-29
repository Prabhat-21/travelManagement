package com.travel.management.dao;


import com.travel.management.entities.MembershipType;
import com.travel.management.entities.Passenger;

public interface PassengerDao {

    Passenger createPassenger(int passengerNumber, String name, MembershipType membership, double balance);
    Passenger getPassenger(String passengerId);
    Passenger updatePassenger(String passengerId, Passenger passenger);
}
