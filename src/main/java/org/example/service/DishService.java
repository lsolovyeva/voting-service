package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.example.model.Dish;
import org.example.model.Restaurant;
import org.example.repository.DishRepository;
import org.example.repository.RestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.example.config.AppConfig.DISHES_CACHE;

@Slf4j
@Service
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    @CacheEvict(value = DISHES_CACHE, allEntries = true)
    public Dish addNewDish(Dish dish, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id=" + restaurantId + " not found."));
        if (restaurantRepository.findByRestaurantIdAndDishName(restaurantId, dish.getName()).isPresent()) {
            throw new UnsupportedOperationException("Unable to add dish=" + dish.getName() + " to restaurant id=" + restaurantId +
                    " : dish already exist.");
        }

        dish.setEnabled(true);
        Dish newDish = dishRepository.save(dish);
        if (restaurant.getDishes() == null) {
            restaurant.setDishes(new ArrayList<>());
        }
        restaurant.getDishes().add(newDish);
        return newDish;
    }

    @Transactional
    @CacheEvict(value = DISHES_CACHE, allEntries = true)
    public boolean updateDish(Dish dish, Long dishId) {
        Dish existingDish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish with id=" + dishId + " not found."));
        existingDish.setName(dish.getName());
        existingDish.setPrice(dish.getPrice());
        dishRepository.save(existingDish);
        return true;
    }

    @Transactional
    @CacheEvict(value = DISHES_CACHE, allEntries = true)
    public boolean changeDishActivation(Long dishId) {
        Dish existingDish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish with id=" + dishId + " not found."));
        existingDish.setEnabled(BooleanUtils.negate(existingDish.isEnabled()));
        dishRepository.save(existingDish);
        return true;
    }

    @Cacheable(value = DISHES_CACHE)
    public List<Dish> getAllDishes(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findByIdWithDishes(restaurantId).orElseThrow(() -> new EntityNotFoundException("Restaurant with id=" + restaurantId + " not found."));
        return restaurant.getDishes();
    }
}
