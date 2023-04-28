package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Restaurant;
import org.example.model.User;
import org.example.repository.RestaurantRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.example.model.Role.ADMIN;

@Slf4j
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public Restaurant save(Restaurant restaurant) { //, Long userId
        /*Optional<User> oUser = userRepository.findById(userId);
        if (oUser.isEmpty() || !(oUser.get().getRoles().contains(ADMIN))) {
            log.error("Unable to add new restaurant: user must be authorized..");
            return null;
        }*/
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(restaurant.getName());
        return restaurantRepository.save(newRestaurant);
    }

    public boolean update(Restaurant restaurant, Long restaurantId) { //Long userId,
        /*Optional<User> oUser = userRepository.findById(userId);

        if (oUser.isEmpty() || !(oUser.get().getRoles().contains(ADMIN))) {
            log.error("Unable to add new restaurant: user must be authorized and restaurant must exist.");
            return false;
        }*/
        Restaurant restaurantToUpdate = restaurantRepository.findById(restaurantId).orElseThrow(() -> new EntityNotFoundException("Restaurant " + restaurant.getName() + " not found."));
        restaurantToUpdate.setName(restaurant.getName());
        restaurantRepository.save(restaurantToUpdate);
        return true;
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }
}
