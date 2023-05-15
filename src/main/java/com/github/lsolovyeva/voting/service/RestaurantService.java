package com.github.lsolovyeva.voting.service;

import com.github.lsolovyeva.voting.config.AppConfig;
import com.github.lsolovyeva.voting.model.Restaurant;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.lsolovyeva.voting.repository.RestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    //@Transactional - already present for CRUD
    @CacheEvict(value = AppConfig.RESTAURANTS_CACHE, allEntries = true)
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    @CacheEvict(value = AppConfig.RESTAURANTS_CACHE, allEntries = true)
    public void update(Restaurant restaurant, Long restaurantId) {
        Restaurant restaurantToUpdate = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant " + restaurant.getName() + " not found."));
        restaurantToUpdate.setName(restaurant.getName());
        restaurantRepository.save(restaurantToUpdate);
    }

    @Cacheable(value = AppConfig.RESTAURANTS_CACHE)
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }
}
