package com.travel.management.dao;

import com.travel.management.entities.Activity;

import java.util.List;

public interface ActivityDao {

    Activity createActivity(String name, String description, int cost, int capacity, String destinationId);

    Activity getActivity(String id);

    List<Activity> getActivitiesByDestinationId(String destinationId);

    List<Activity> getAllActivities();

    Activity updateActivity(String activityId, Activity activity);

}
