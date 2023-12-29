package com.travel.management.controller;


import com.travel.management.dto.RegisterPackageRequestDto;
import com.travel.management.entities.TravelPackage;
import com.travel.management.service.PackageService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/packages")
public class PackageController {

    private final PackageService packageService;


    @PostMapping()
    public TravelPackage registerPackage(@Validated @Valid @NotNull RegisterPackageRequestDto registerPackageRequestDto) {
        return packageService.registerPackage(registerPackageRequestDto.getName(), registerPackageRequestDto.getCapacity(), registerPackageRequestDto.getCost(), registerPackageRequestDto.getDestinations());
    }

    @GetMapping("/{id}")
    public TravelPackage getTravelPackage(@PathVariable("id") String id) {
        return packageService.getTravelPackage(id);
    }
}
