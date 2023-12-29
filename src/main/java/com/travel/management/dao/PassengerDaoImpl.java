package com.travel.management.dao;

import com.travel.management.entities.MembershipType;
import com.travel.management.entities.Passenger;
import com.travel.management.exception.AppException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class PassengerDaoImpl implements PassengerDao {

    public Map<String, Passenger> passengerMap = new HashMap<String, Passenger>();

    public Passenger createPassenger(int passengerNumber, String name, MembershipType membership, double balance) {
        Passenger passenger = new Passenger(UUID.randomUUID().toString(), passengerNumber, name, membership, balance);
        passengerMap.put(passenger.getId(), passenger);
        return passenger;
    }

    public Passenger getPassenger(String passengerId) {
        Passenger passenger = passengerMap.get(passengerId);
        if (passenger == null) {
            throw new AppException("Passenger does not exist");
        }
        return passenger;
    }

    public Passenger updatePassenger(String passengerId, Passenger passenger) {
        passengerMap.put(passengerId, passenger);
        return passenger;
    }
}
