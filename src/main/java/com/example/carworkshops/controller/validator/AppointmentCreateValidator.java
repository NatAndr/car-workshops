package com.example.carworkshops.controller.validator;

import com.example.carworkshops.controller.dto.AppointmentCreateDto;
import com.example.carworkshops.model.Appointment;
import com.example.carworkshops.repository.AppointmentRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class AppointmentCreateValidator implements Validator {
    private static final String EXISTS = "already exists";

    private final AppointmentRepository appointmentRepository;

    public AppointmentCreateValidator(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return AppointmentCreateDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AppointmentCreateDto dto = (AppointmentCreateDto) o;

        List<Appointment> appointments = appointmentRepository.findByUser_idAndAppointmentAtAndDeletedAtIsNull(
            dto.getUserId(),
            dto.getAppointmentAt()
        );

        if (!CollectionUtils.isEmpty(appointments)) {
            errors.rejectValue("appointmentAt", EXISTS, EXISTS);
        }
    }
}
