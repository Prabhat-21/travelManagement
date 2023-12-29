/**
 * The Activity service is responsible for creating new activity, fetch activity, getAllActivities() and
 * update activity based on activityId
 *
 * @author Prabhat Sharma
 * @version 1.0
 * @since 2023-12-29
 */


package com.travel.management.service;

import com.travel.management.dao.ActivityDao;
import com.travel.management.entities.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityDao activityDao;

    /**
     * The create activity is responsible for creating new activity based on name, description, capacity and destinationId.
     * @param name name of the activity
     * @param capacity total capacity of the activity
     * @param cost   total cost of the activity
     * @param description description of the activity
     * @param destinationId Id of the destination on which activity is taking place.
     * @return Activity : Created activity entity.
     */
    public Activity createActivity(String name, String description, int cost, int capacity, String destinationId) {
        return activityDao.createActivity(name, description, cost, capacity, destinationId);
    }

    /**
     * The create activity is responsible for creating new activity based on name, description, capacity and destinationId.
     * @param id ActivityId(PrimaryKey)
     * @return Activity : Fetched activity w.r.t Id
     */
    public Activity getActivity(String id) {
        return activityDao.getActivity(id);

    }

    /**
     * Fetches all the activities for a given destinationId.
     * @param destinationId DestinationId(PrimaryKey)
     * @return List<Activity> on that destination
     */
    public List<Activity> getActivitiesByDestinationId(String destinationId) {
        return activityDao.getActivitiesByDestinationId(destinationId);
    }

    /**
     * Fetches all activities present
     * @return List<Activity> : Fetches all activities.
     */
    public List<Activity> getAllActivities() {
        return activityDao.getAllActivities();
    }

    /**
     *Update an activity based on activityId and new activity
     * @return updated activity on that Id.
     */
    public Activity updateActivity(String activityId, Activity activity) {
        return activityDao.updateActivity(activityId, activity);
    }
}
