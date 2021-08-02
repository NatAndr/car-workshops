package com.example.carworkshops.listener;

import com.example.carworkshops.model.CarTrademark;
import com.example.carworkshops.model.City;
import com.example.carworkshops.model.Country;
import com.example.carworkshops.model.User;
import com.example.carworkshops.repository.CarTrademarkRepository;
import com.example.carworkshops.repository.CityRepository;
import com.example.carworkshops.repository.CountryRepository;
import com.example.carworkshops.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Slf4j
@Component
@Profile("upload")
public class DataUploader implements ApplicationListener<ApplicationReadyEvent> {
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final CarTrademarkRepository carTrademarkRepository;
    private final UserRepository userRepository;

    public DataUploader(CountryRepository countryRepository,
                        CityRepository cityRepository,
                        CarTrademarkRepository carTrademarkRepository,
                        UserRepository userRepository) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.carTrademarkRepository = carTrademarkRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("Start upload data");

        addCities();
        addCarTrademarks();
        addUsers();

        log.info("End upload data");
    }

    private void addCities() {
        Country germany = Country.builder()
            .id(1)
            .name("Germany")
            .build();

        countryRepository.save(germany);

        City berlin = City.builder()
            .id(2)
            .name("Berlin")
            .country(germany)
            .build();

        City dusseldorf = City.builder()
            .id(3)
            .name("Dusseldorf")
            .country(germany)
            .build();

        cityRepository.save(berlin);
        cityRepository.save(dusseldorf);
    }

    private void addCarTrademarks() {
        CarTrademark bmw = CarTrademark.builder()
            .name("BMW")
            .build();

        CarTrademark vw = CarTrademark.builder()
            .name("VW")
            .build();

        CarTrademark audi = CarTrademark.builder()
            .name("Audi")
            .build();

        carTrademarkRepository.save(bmw);
        carTrademarkRepository.save(vw);
        carTrademarkRepository.save(audi);
    }

    private void addUsers() {
        City city = cityRepository.findById(2L).orElseThrow(NoSuchElementException::new);

        User user = User.builder()
            .name("user1")
            .email("email1@google.com")
            .city(city)
            .postalCode("123456")
            .build();

        userRepository.save(user);
    }
}
