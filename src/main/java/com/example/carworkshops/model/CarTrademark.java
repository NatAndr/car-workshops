package com.example.carworkshops.model;

import com.example.carworkshops.model.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "car_trademark")
public class CarTrademark extends BaseEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;
}
