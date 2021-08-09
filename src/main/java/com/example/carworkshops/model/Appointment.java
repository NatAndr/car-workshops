package com.example.carworkshops.model;

import com.example.carworkshops.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "appointment",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "appointment_at"}, name = "unique_user_appointment"),
    }
)
public class Appointment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "trademark_id", nullable = false)
    private CarTrademark carTrademark;

    @ManyToOne
    @JoinColumn(name = "workshop_id", nullable = false)
    private Workshop workshop;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", name = "appointment_at")
    @JsonProperty("appointment_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime appointmentAt;
}
