package com.travel.management.dao;

import com.travel.management.entities.OrderActivityMapping;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class OrderActivityMappingDaoImpl implements OrderActivityMappingDao {
    public Map<String, OrderActivityMapping> orderActivityMap = new HashMap<String, OrderActivityMapping>();

    public OrderActivityMapping createOrderActivityMapping(String orderId, String activityId) {
        OrderActivityMapping orderActivityMapping = new OrderActivityMapping(UUID.randomUUID().toString(), orderId, activityId);
        orderActivityMap.put(orderActivityMapping.getId(), orderActivityMapping);
        return orderActivityMapping;
    }

    public List<String> getActivitiesByOrderId(String orderId) {
        List<OrderActivityMapping> orderActivityMappings = orderActivityMap.values().stream()
                .filter(orderActivityMapping -> Objects.equals(orderActivityMapping.getOrderId(), orderId))
                .collect(Collectors.toList());
        List<String> activities = new ArrayList<>();
        orderActivityMappings.forEach(orderActivityMapping -> activities.add(orderActivityMapping.getActivityId()));
        return activities;
    }
}
