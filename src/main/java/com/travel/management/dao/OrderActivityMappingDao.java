package com.travel.management.dao;

import com.travel.management.entities.OrderActivityMapping;

import java.util.List;

public interface OrderActivityMappingDao {

    OrderActivityMapping createOrderActivityMapping(String orderId, String activityId);

    List<String> getActivitiesByOrderId(String orderId);

}
