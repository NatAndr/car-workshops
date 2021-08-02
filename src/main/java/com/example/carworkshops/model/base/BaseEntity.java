package com.example.carworkshops.model.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@SuperBuilder(toBuilder = true)
@MappedSuperclass
@NoArgsConstructor
public class BaseEntity implements Modifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", name = "created_at")
    @JsonProperty("created_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Builder.Default
    protected ZonedDateTime createdAt = ZonedDateTime.now();

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", name = "update_at")
    @JsonProperty("update_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Builder.Default
    protected ZonedDateTime updatedAt = ZonedDateTime.now();

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", name = "deleted_at")
    @JsonProperty("deleted_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    protected ZonedDateTime deletedAt;

    @Override
    public void delete() {
        this.deletedAt = ZonedDateTime.now();
    }
}
