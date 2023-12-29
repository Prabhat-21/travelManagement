/**
 * This service is responsible for handling orders, get passengers details
 */

package com.travel.management.service;

import com.travel.management.dao.ActivityDao;
import com.travel.management.dao.OrderActivityMappingDao;
import com.travel.management.dao.OrderDao;
import com.travel.management.dao.PassengerDao;
import com.travel.management.dao.TravelPackageDao;
import com.travel.management.dto.PassengerActivityDetailsDto;
import com.travel.management.entities.Activity;
import com.travel.management.entities.MembershipType;
import com.travel.management.entities.Order;
import com.travel.management.entities.Passenger;
import com.travel.management.entities.TravelPackage;
import com.travel.management.exception.AppException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerPassengerService {
    private final PassengerDao passengerDao;

    private final ActivityDao activityDao;

    private final TravelPackageDao travelPackageDao;

    private final OrderDao orderDao;

    private final OrderActivityMappingDao orderActivityMappingDao;

    /**
     * This method places order
     *
     * @param packageId   packageId for the order
     * @param passengerId passengerId for the order
     * @param activities  list of activities for the order
     * @return Order entity : orderId, packageId, and passengerId.
     */
    public Order placeOrder(String packageId, String passengerId, List<String> activities) {
        Passenger passenger = passengerDao.getPassenger(passengerId);
        double balance = passenger.getBalance();
        activities.forEach(activityId -> {
            if (activityDao.getActivity(activityId).getRemainingCapacity() < 1) {
                throw new AppException("Capacity is full in this activity : " + activityId);
            }
        });
        int totalActivityCost = activities.stream().map(activityId -> activityDao.getActivity(activityId).getCost())
                .reduce(Integer::sum).orElse(0);
        TravelPackage travelPackage = travelPackageDao.getTravelPackage(packageId);
        if (travelPackage == null) {
            throw new AppException("package does not exist with this id : " + packageId);
        }
        int enrolled = travelPackage.getEnrolled();
        if (enrolled >= travelPackage.getCapacity()) {
            throw new AppException("No space left in the package : " + packageId);
        }
        MembershipType membershipType = passenger.getMembership();

        int packageCost = travelPackage.getCost();

        int totalCost = packageCost + membershipType.getFinalCost(totalActivityCost);
        if (balance < totalCost) {
            throw new AppException("Account balance is low for placing this order");
        }

        passenger.setBalance(balance - totalCost);
        passengerDao.updatePassenger(passengerId, passenger);
        travelPackage.setEnrolled(enrolled + 1);
        travelPackageDao.updateTravelPackage(packageId, travelPackage);
        Order order = orderDao.createOrder(packageId, passengerId);

        activities.forEach(activityId -> {
            orderActivityMappingDao.createOrderActivityMapping(order.getId(), activityId);
            Activity activity = activityDao.getActivity(activityId);
            activity.setRemainingCapacity(activity.getRemainingCapacity() - 1);
            activityDao.updateActivity(activityId, activity);
        });
        return order;
    }


    /**
     * @param passengerId Id of the passengerId
     * @return List<Activity> for the particular passengerId.
     */
    public PassengerActivityDetailsDto getPassengerActivitiesById(String passengerId) {
        PassengerActivityDetailsDto passengerActivityDetailsDto = new PassengerActivityDetailsDto();
        Passenger passenger = passengerDao.getPassenger(passengerId);
        passengerActivityDetailsDto.setName(passenger.getName());
        passengerActivityDetailsDto.setPassengerNo(passenger.getPassengerNo());
        passengerActivityDetailsDto.setBalance(passenger.getBalance());
        List<Order> orders = orderDao.getOrdersByPassenger(passengerId);
        List<Activity> activityList = orders.stream().flatMap(order ->
                orderActivityMappingDao.getActivitiesByOrderId(order.getId()).stream()
                        .map(activityId -> activityDao.getActivity(activityId))
        ).collect(Collectors.toList());
        passengerActivityDetailsDto.setActivityList(activityList);

        return passengerActivityDetailsDto;
    }
}
