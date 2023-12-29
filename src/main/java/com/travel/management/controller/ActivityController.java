package com.travel.management.controller;

import com.travel.management.dto.ActivityRequestDto;
import com.travel.management.entities.Activity;
import com.travel.management.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping()
    public Activity createActivity(@Validated @Valid @NotNull @RequestBody ActivityRequestDto activityRequestDto) {
        return activityService.createActivity(activityRequestDto.getName(), activityRequestDto.getDescription(), activityRequestDto.getCost(), activityRequestDto.getCapacity(), activityRequestDto.getDestinationId());
    }

    @GetMapping("/{id}")
    public Activity getActivity(@PathVariable("id") String id) {
        return activityService.getActivity(id);
    }

    @GetMapping()
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    @PutMapping("/{id}")
    public Activity updateActivity(@PathVariable("id") String activityId, @RequestBody Activity activity) {
        return activityService.updateActivity(activityId, activity);
    }

    @GetMapping("/destinations/{destinationId}")
    public List<Activity> getActivitiesByDestinationId(@PathVariable("destinationId") String destinationId) {
        return activityService.getActivitiesByDestinationId(destinationId);
    }
}
