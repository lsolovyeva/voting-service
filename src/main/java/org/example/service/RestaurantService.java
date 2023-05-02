package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Restaurant;
import org.example.repository.RestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.config.AppConfig.RESTAURANTS_CACHE;

@Slf4j
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    //@Transactional - already present for CRUD
    @CacheEvict(value = RESTAURANTS_CACHE, allEntries = true)
    public Restaurant save(Restaurant restaurant) {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(restaurant.getName());
        return restaurantRepository.save(newRestaurant);
    }

    @Transactional
    @CacheEvict(value = RESTAURANTS_CACHE, allEntries = true)
    public boolean update(Restaurant restaurant, Long restaurantId) {
        Restaurant restaurantToUpdate = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant " + restaurant.getName() + " not found."));
        restaurantToUpdate.setName(restaurant.getName());
        restaurantRepository.save(restaurantToUpdate);
        return true;
    }

    @Cacheable(value = RESTAURANTS_CACHE)
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }
}
