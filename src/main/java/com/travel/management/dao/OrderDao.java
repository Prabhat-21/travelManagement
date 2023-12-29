package com.travel.management.dao;

import com.travel.management.entities.Order;

import java.util.List;

public interface OrderDao {

    Order createOrder(String packageId, String passengerId);

    List<String> getPassengersByPackageId(String packageId);

    List<Order> getOrdersByPassenger(String passengerId);
}
