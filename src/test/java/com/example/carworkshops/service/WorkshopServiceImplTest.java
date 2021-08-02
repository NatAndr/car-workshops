package com.example.carworkshops.service;

import com.example.carworkshops.controller.dto.WorkshopCreateDto;
import com.example.carworkshops.model.CarTrademark;
import com.example.carworkshops.model.City;
import com.example.carworkshops.model.Country;
import com.example.carworkshops.model.Workshop;
import com.example.carworkshops.repository.WorkshopRepository;
import com.example.carworkshops.service.impl.WorkshopServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class WorkshopServiceImplTest {
    @MockBean
    private WorkshopRepository workshopRepository;
    @MockBean
    private CarTrademarkService carTrademarkService;
    @MockBean
    private CityService cityService;
    private WorkshopService workshopService;

    @Before
    public void setUp() throws Exception {
        workshopService = new WorkshopServiceImpl(workshopRepository, carTrademarkService, cityService);
    }

    @Test
    public void testCreate() {
        CarTrademark carTrademark = getCarTrademark();
        City city = getCity();
        Workshop workshop = getWorkshop();

        when(carTrademarkService.getById(anyLong()))
            .thenReturn(carTrademark);

        when(cityService.getById(anyLong()))
            .thenReturn(city);

        WorkshopCreateDto dto = WorkshopCreateDto.builder()
            .name("name")
            .cityId(1)
            .trademarkIds(Arrays.asList(2L, 3L))
            .build();

        when(workshopRepository.save(any(Workshop.class)))
            .thenReturn(workshop);

        Workshop actual = workshopService.create(dto);

        assertNotNull(actual);
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());
    }

    @Test
    public void testGetById() {
        Workshop workshop = getWorkshop();
        when(workshopRepository.findByIdAndDeletedAtIsNull(anyLong()))
            .thenReturn(Optional.ofNullable(workshop));

        Workshop actual = workshopService.getById(1);

        assertNotNull(actual);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetById_noSuch_shouldThrowException() {
        when(workshopRepository.findByIdAndDeletedAtIsNull(anyLong()))
            .thenReturn(Optional.empty());

        Workshop actual = workshopService.getById(1);

        assertNotNull(actual);
    }

    @Test
    public void testDelete() {
        Workshop workshop = getWorkshop();

        when(workshopRepository.findByIdAndDeletedAtIsNull(anyLong()))
            .thenReturn(Optional.of(workshop));

        workshopService.delete(1L);

        verify(workshopRepository, times(1)).save(any(Workshop.class));

        assertNotNull(workshop.getDeletedAt());
    }

    @Test
    public void testFindByCity_notEmpty() {
        Workshop workshop = getWorkshop();

        when(workshopRepository.findByCity_IdAndDeletedAtIsNull(anyLong()))
            .thenReturn(Collections.singletonList(workshop));

        List<Workshop> actual = workshopService.findByCity(1);

        assertFalse(CollectionUtils.isEmpty(actual));
    }

    @Test
    public void testFindByCity_empty() {
        when(workshopRepository.findByCity_IdAndDeletedAtIsNull(anyLong()))
            .thenReturn(Collections.emptyList());

        List<Workshop> actual = workshopService.findByCity(1);

        assertTrue(CollectionUtils.isEmpty(actual));
    }

    @Test
    public void testFindAll() {
        Workshop workshop = getWorkshop();

        when(workshopRepository.findAllByDeletedAtIsNull())
            .thenReturn(Collections.singletonList(workshop));

        List<Workshop> actual = workshopService.findAll();

        assertFalse(CollectionUtils.isEmpty(actual));
    }

    private Workshop getWorkshop() {
        CarTrademark carTrademark = getCarTrademark();
        City berlin = getCity();

        return Workshop.builder()
            .id(3)
            .name("workshop1")
            .city(berlin)
            .postalCode("123456")
            .trademarks(new HashSet<>(Collections.singletonList(carTrademark)))
            .build();
    }

    private CarTrademark getCarTrademark() {
        return CarTrademark.builder()
            .id(1)
            .name("VW")
            .build();
    }

    private City getCity() {
        Country germany = Country.builder()
            .id(2)
            .name("Germany")
            .build();

        return City.builder()
            .id(4)
            .name("Berlin")
            .country(germany)
            .build();
    }

}
