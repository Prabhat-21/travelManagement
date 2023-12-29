package com.travel.management.controller;

import com.travel.management.dto.DestinationRequestDto;
import com.travel.management.entities.Destination;
import com.travel.management.service.DestinationService;
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

@RestController
@AllArgsConstructor
@RequestMapping("/destinations")
public class DestinationController {
    private final DestinationService destinationService;

    @PostMapping()
    public Destination createDestination(@Valid @Validated @NotNull @RequestBody DestinationRequestDto destinationRequestDto) {
        return destinationService.createDestination(destinationRequestDto.getName());
    }

    @GetMapping("/{id}")
    public Destination getDestination(@PathVariable("id") String id) {
        return destinationService.getDestination(id);
    }

    @PutMapping("/{id}")
    public Destination updateDestination(@PathVariable("id") String id, Destination destination) {
        return destinationService.updateDestination(id, destination);
    }
}
