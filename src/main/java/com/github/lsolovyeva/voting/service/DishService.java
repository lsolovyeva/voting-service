package com.github.lsolovyeva.voting.service;

import com.github.lsolovyeva.voting.config.AppConfig;
import com.github.lsolovyeva.voting.exception.ItemMappingException;
import com.github.lsolovyeva.voting.model.Dish;
import com.github.lsolovyeva.voting.model.Restaurant;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.lsolovyeva.voting.repository.DishRepository;
import com.github.lsolovyeva.voting.repository.RestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    @CacheEvict(value = AppConfig.DISHES_CACHE, key = "#dish.restaurant.id", condition = "#dish.restaurant!=null")
    public Dish add(Dish dish, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id=" + restaurantId + " not found."));
        if (dishRepository.findByRestaurantIdAndName(restaurantId, dish.getName()).isPresent()) {
            throw new ItemMappingException("Unable to add dish=" + dish.getName() + " to restaurant id=" +
                    restaurantId + " : dish already exist.");
        }

        dish.setRestaurant(restaurant);
        Dish newDish = dishRepository.save(dish);
        if (restaurant.getDishes() == null) {
            restaurant.setDishes(new ArrayList<>());
        }
        restaurant.getDishes().add(newDish);
        return newDish;
    }

    @Transactional
    @CacheEvict(value = AppConfig.DISHES_CACHE, key = "#dish.restaurant.id", condition = "#dish.restaurant!=null")
    public void update(Dish dish, Long dishId) {
        Dish existingDish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish with id=" + dishId + " not found."));
        existingDish.setName(dish.getName());
        existingDish.setPrice(dish.getPrice());
        dishRepository.save(existingDish);
    }

    @Cacheable(value = AppConfig.DISHES_CACHE)
    public List<Dish> getAllForToday(Long restaurantId) {
        return dishRepository.findByIdWithDishesForToday(restaurantId, LocalDate.now().atTime(LocalTime.MIDNIGHT),
                LocalDate.now().atTime(LocalTime.MAX));
    }

    public List<Dish> getAll(Long restaurantId) {
        return dishRepository.findAllByRestaurantId(restaurantId);
    }
}
