package com.example.carworkshops.model;

import com.example.carworkshops.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "workshop")
public class Workshop extends BaseEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "trademark_workshop",
            joinColumns = @JoinColumn(name = "trademark_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "workshop_id", referencedColumnName = "id"))
    private Set<CarTrademark> trademarks;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "postal_code")
    @JsonProperty("postal_code")
    private String postalCode;
}
