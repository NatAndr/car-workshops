package com.example.carworkshops.controller;

import com.example.carworkshops.model.CarTrademark;
import com.example.carworkshops.model.DataResultObject;
import com.example.carworkshops.service.CarTrademarkService;
import com.example.carworkshops.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/trademark")
public class CarTrademarkController {
    private final CarTrademarkService carTrademarkService;

    @Autowired
    public CarTrademarkController(CarTrademarkService carTrademarkService) {
        this.carTrademarkService = carTrademarkService;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResultObject<List<CarTrademark>>> list() {

        return HttpUtils.ok(carTrademarkService.findAll());
    }
}
