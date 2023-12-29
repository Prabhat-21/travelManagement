package com.travel.management.dao;

import com.travel.management.entities.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class OrderDaoImpl implements OrderDao {

    public Map<String, Order> orderMap = new HashMap<String, Order>();

    public Order createOrder(String packageId, String passengerId) {
        Order order = new Order(UUID.randomUUID().toString(), packageId, passengerId);
        orderMap.put(order.getId(), order);
        return order;
    }

    public List<String> getPassengersByPackageId(String packageId) {
        List<Order> orders = orderMap.values().stream().toList();
        List<String> passengers = new ArrayList<>();
        orders.forEach(order -> {
            if (Objects.equals(order.getPackageId(), packageId)) {
                passengers.add(order.getPassengerId());
            }
        });
        return passengers;
    }

    public List<Order> getOrdersByPassenger(String passengerId) {
        return orderMap.values().stream().filter(order -> Objects.equals(order.getPassengerId(), passengerId)).collect(Collectors.toList());
    }
}
