package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Restaurant;
import org.example.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    //@Transactional - already present for CRUD
    public Restaurant save(Restaurant restaurant) {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(restaurant.getName());
        return restaurantRepository.save(newRestaurant);
    }

    @Transactional
    public boolean update(Restaurant restaurant, Long restaurantId) {
        Restaurant restaurantToUpdate = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant " + restaurant.getName() + " not found."));
        restaurantToUpdate.setName(restaurant.getName());
        restaurantRepository.save(restaurantToUpdate);
        return true;
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }
}
