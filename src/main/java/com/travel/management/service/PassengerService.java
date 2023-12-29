/**
 * This service is responsible for creating, updating, and fetching passengers
 */

package com.travel.management.service;

import com.travel.management.dao.PassengerDao;
import com.travel.management.entities.MembershipType;
import com.travel.management.entities.Passenger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerDao passengerDao;


    /**
     * @param passengerNumber
     * @param name
     * @param balance
     * @param membership
     * @return Passenger : details of the passenger
     */
    public Passenger createPassenger(int passengerNumber, String name, MembershipType membership, double balance) {
        return passengerDao.createPassenger(passengerNumber, name, membership, balance);
    }

    /**
     * @param passengerId
     * @return Passenger : fetches Passenger for Id.
     */
    public Passenger getPassenger(String passengerId) {
        return passengerDao.getPassenger(passengerId);
    }

    /**
     * @param passengerId
     * @param passenger
     * @return Passenger : updates passenger for Id.
     */
    public Passenger updatePassenger(String passengerId, Passenger passenger) {
        return passengerDao.updatePassenger(passengerId, passenger);
    }


}
