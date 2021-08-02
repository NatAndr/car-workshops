package com.example.carworkshops.controller;

import com.example.carworkshops.controller.dto.WorkshopCreateDto;
import com.example.carworkshops.controller.validator.WorkshopCreateValidator;
import com.example.carworkshops.model.DataResultObject;
import com.example.carworkshops.model.Workshop;
import com.example.carworkshops.service.WorkshopService;
import com.example.carworkshops.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/workshop")
public class WorkshopController {
    private final WorkshopService workshopService;
    private final WorkshopCreateValidator workshopCreateValidator;

    @Autowired
    public WorkshopController(WorkshopService workshopService,
                              WorkshopCreateValidator workshopCreateValidator) {
        this.workshopService = workshopService;
        this.workshopCreateValidator = workshopCreateValidator;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResultObject<List<Workshop>>> list() {

        return HttpUtils.ok(workshopService.findAll());
    }

    @GetMapping(value = "/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResultObject<Workshop>> get(
        @PathVariable Long id
    ) {

        return HttpUtils.ok(workshopService.getById(id));
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResultObject<Workshop>> create(
        @Valid @RequestBody WorkshopCreateDto workshopCreateDto
    ) {
        Workshop workshop = workshopService.create(workshopCreateDto);

        return HttpUtils.withStatus(workshop, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}/")
    public ResponseEntity<DataResultObject<Void>> delete(
        @PathVariable Long id
    ) {
        workshopService.delete(id);

        return HttpUtils.noContent();
    }

    @InitBinder("workshopCreateDto")
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(workshopCreateValidator);
    }
}
