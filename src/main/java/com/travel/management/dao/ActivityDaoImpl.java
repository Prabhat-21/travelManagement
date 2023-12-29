package com.travel.management.dao;

import com.travel.management.entities.Activity;
import com.travel.management.exception.AppException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ActivityDaoImpl implements ActivityDao {


    public Map<String, Activity> activityMap = new HashMap<String, Activity>();

    public Activity createActivity(String name, String description, int cost, int capacity, String destinationId) {
        Activity activity = new Activity(UUID.randomUUID().toString(), name, description, cost, capacity, destinationId);
        activityMap.put(activity.getId(), activity);
        return activity;
    }

    public Activity getActivity(String id) {
        Activity activity = activityMap.get(id);
        if (activity == null) {
            throw new AppException("Activity does not exist. id: " + id);
        }
        return activity;
    }

    public List<Activity> getActivitiesByDestinationId(String destinationId) {
        List<Activity> activityList = activityMap.values().stream().toList();
        return activityList.stream().filter(activity -> Objects.equals(activity.getDestinationId(), destinationId)).collect(Collectors.toList());
    }

    public List<Activity> getAllActivities() {
        return activityMap.values().stream().toList();
    }

    public Activity updateActivity(String activityId, Activity activity) {
        activityMap.put(activityId, activity);
        return activity;
    }
}
