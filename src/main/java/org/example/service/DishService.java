package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.example.model.Dish;
import org.example.model.Restaurant;
import org.example.repository.DishRepository;
import org.example.repository.RestaurantRepository;
import org.example.repository.UserRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Dish addNewDish(Dish dish, Long restaurantId) { //, Long userId
        //Optional<User> oUser = userRepository.findById(userId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() ->
                new EntityNotFoundException("Restaurant with id=" + restaurantId + " not found."));
        if (restaurantRepository.findByRestaurantIdAndDishName(restaurantId, dish.getName()).isPresent()) {
            throw new UnsupportedOperationException("Unable to add dish=" + dish.getName() + " to restaurant id=" + restaurantId +
                    " : dish already exist.");
        }
        //List<Dish> existingDishes = null; //dishRepository.findByRestaurantId(restaurantId);

        /*if (oUser.isEmpty() || !(oUser.get().getRoles().contains(ADMIN)) || oRestaurant.isEmpty()) {
            log.error("Unable to add new dish: user must be authorized and restaurant must exist.");
            return null;
        }*/

        /*if (existingDishes.contains(dish)) {
            log.error("Dish already belongs to this restaurant");
            return false;
        }*/
        dish.setEnabled(true);
        Dish newDish = dishRepository.save(dish);
        //List<Dish> dishes =  new ArrayList<>(oRestaurant.get().getDishes() == null ? Collections.emptyList() : oRestaurant.get().getDishes());
        //dishes.add(newDish);
        //Restaurant updatedRestaurant = oRestaurant.get();
        //updatedRestaurant.setDishes(dishes);
        if (restaurant.getDishes() == null) {
            restaurant.setDishes(new ArrayList<>());
        }
        restaurant.getDishes().add(newDish);
        //restaurantRepository.save(updatedRestaurant);
        return newDish;
    }

    @Transactional
    @Modifying
    public boolean updateDish(Dish dish, Long dishId) { // Long userId,
        //Optional<User> oUser = userRepository.findById(userId);
        Dish existingDish = dishRepository.findById(dishId).orElseThrow(() ->
                new EntityNotFoundException("Dish with id=" + dishId + " not found."));

       /* if (oUser.isEmpty() || !(oUser.get().getRoles().contains(ADMIN)) || existingDish.isEmpty()) {
            log.error("Unable to update dish: user must be authorized and dish must exist.");
            return false;
        }*/
        existingDish.setName(dish.getName());
        existingDish.setPrice(dish.getPrice());
        dishRepository.save(existingDish);
        return true;
    }

    @Transactional
    @Modifying
    public boolean changeDishActivation(Long dishId) { //Long userId,
        //Optional<User> oUser = userRepository.findById(userId);
        Dish existingDish = dishRepository.findById(dishId).orElseThrow(() ->
                new EntityNotFoundException("Dish with id=" + dishId + " not found."));

       /* if (oUser.isEmpty() || !(oUser.get().getRoles().contains(ADMIN)) || existingDish.isEmpty()) {
            log.error("Unable to update dish: user must be authorized and dish must exist.");
            return false;
        }*/
        existingDish.setEnabled(BooleanUtils.negate(existingDish.isEnabled()));
        dishRepository.save(existingDish);
        return true;
    }

    @Transactional
    public List<Dish> getAllDishes(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findByIdWithDishes(restaurantId).orElseThrow(() -> new EntityNotFoundException("Restaurant with id=" + restaurantId + " not found."));
        return restaurant.getDishes();
    }
}
