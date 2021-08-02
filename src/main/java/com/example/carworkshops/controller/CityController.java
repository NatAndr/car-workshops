package com.example.carworkshops.controller;

import com.example.carworkshops.model.City;
import com.example.carworkshops.model.DataResultObject;
import com.example.carworkshops.model.Workshop;
import com.example.carworkshops.service.CityService;
import com.example.carworkshops.service.WorkshopService;
import com.example.carworkshops.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/city")
public class CityController {
    private final CityService cityService;
    private final WorkshopService workshopService;

    @Autowired
    public CityController(CityService cityService,
                          WorkshopService workshopService) {
        this.cityService = cityService;
        this.workshopService = workshopService;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResultObject<List<City>>> list() {

        return HttpUtils.ok(cityService.findAll());
    }

    @GetMapping(value = "/{id}/workshop/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResultObject<List<Workshop>>> getWorkShops(
        @PathVariable Long id
    ) {

        return HttpUtils.ok(workshopService.findByCity(id));
    }
}
