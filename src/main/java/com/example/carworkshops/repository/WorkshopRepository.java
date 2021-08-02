package com.example.carworkshops.repository;

import com.example.carworkshops.model.Workshop;
import com.example.carworkshops.repository.base.BaseRepository;

import java.util.List;

public interface WorkshopRepository extends BaseRepository<Workshop, Long> {

    List<Workshop> findByCity_IdAndDeletedAtIsNull(long cityId);

    List<Workshop> findByNameAndDeletedAtIsNull(String email);
}
