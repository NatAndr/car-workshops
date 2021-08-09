package com.example.carworkshops.controller;

import com.example.carworkshops.controller.dto.AppointmentChangeDto;
import com.example.carworkshops.controller.dto.AppointmentCreateDto;
import com.example.carworkshops.controller.validator.AppointmentCreateValidator;
import com.example.carworkshops.model.Appointment;
import com.example.carworkshops.model.DataResultObject;
import com.example.carworkshops.service.AppointmentService;
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
@RequestMapping("/api/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final AppointmentCreateValidator appointmentCreateValidator;

    @Autowired
    public AppointmentController(AppointmentService appointmentService,
                                 AppointmentCreateValidator appointmentCreateValidator) {
        this.appointmentService = appointmentService;
        this.appointmentCreateValidator = appointmentCreateValidator;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResultObject<List<Appointment>>> list() {

        return HttpUtils.ok(appointmentService.findAll());
    }

    @GetMapping(value = "/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResultObject<Appointment>> get(
        @PathVariable Long id
    ) {

        return HttpUtils.ok(appointmentService.getById(id));
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResultObject<Appointment>> create(
        @Valid @RequestBody AppointmentCreateDto appointmentCreateDto
    ) {
        Appointment appointment = appointmentService.create(appointmentCreateDto);

        return HttpUtils.withStatus(appointment, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}/", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResultObject<Appointment>> changeDateTime(
        @PathVariable Long id,
        @Valid @RequestBody AppointmentChangeDto appointmentChangeDto
    ) {
        Appointment appointment = appointmentService.changeDateTime(id, appointmentChangeDto);

        return HttpUtils.ok(appointment);
    }

    @DeleteMapping(value = "/{id}/")
    public ResponseEntity<DataResultObject<Void>> delete(
        @PathVariable Long id
    ) {
        appointmentService.delete(id);

        return HttpUtils.noContent();
    }

    @InitBinder("appointmentCreateDto")
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(appointmentCreateValidator);
    }
}
