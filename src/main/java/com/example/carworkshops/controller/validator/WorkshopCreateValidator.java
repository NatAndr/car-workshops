package com.example.carworkshops.controller.validator;

import com.example.carworkshops.controller.dto.WorkshopCreateDto;
import com.example.carworkshops.model.Workshop;
import com.example.carworkshops.repository.WorkshopRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class WorkshopCreateValidator implements Validator {
    private static final String EXISTS = "already exists";

    private final WorkshopRepository workshopRepository;

    public WorkshopCreateValidator(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return WorkshopCreateDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        WorkshopCreateDto dto = (WorkshopCreateDto) o;

        List<Workshop> workshops = workshopRepository.findByNameAndDeletedAtIsNull(dto.getName());

        if (!CollectionUtils.isEmpty(workshops)) {
            errors.rejectValue("name", EXISTS, EXISTS);
        }
    }
}
