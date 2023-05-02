package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.Dish;
import org.example.repository.DishRepository;
import org.example.repository.RestaurantRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.example.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {

    private DishService dishService;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        dishService = new DishService(dishRepository, restaurantRepository);
        testDish.setId(1L);
        lenient().when(userRepository.findById(ADMIN_ID)).thenReturn(Optional.of(testAdmin));
    }

    @Test
    void addNewDish() {
        when(dishRepository.save(any())).thenReturn(testDish);
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(testRestaurant));
        Dish dish = dishService.addNewDish(testDish, RESTAURANT_ID);
        assertEquals(testDish.getName(), dish.getName());
    }

    @Test
    void NotAddNewDishWhenAlreadyPresent() {
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(testRestaurant));
        when(restaurantRepository.findByRestaurantIdAndDishName(any(), any())).thenReturn(Optional.of(testRestaurant));
        assertThrows(UnsupportedOperationException.class, () -> dishService.addNewDish(testDish, RESTAURANT_ID));
    }

    @Test
    void NotAddNewDishWhenRestaurantNotFound() {
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> dishService.addNewDish(testDish, RESTAURANT_ID));
    }

    @Test
    void updateDish() {
        when(dishRepository.findById(any())).thenReturn(Optional.of(testDish));
        when(dishRepository.save(any())).thenReturn(testDish);
        assertTrue(dishService.updateDish(testDish, DISH_ID));
    }

    @Test
    void notUpdateDishWhenNotFound() {
        when(dishRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> dishService.updateDish(testDish, DISH_ID));
    }

    @Test
    void deactivateDish() {
        when(dishRepository.findById(any())).thenReturn(Optional.of(testDish));
        assertTrue(dishService.changeDishActivation(DISH_ID));
    }
}
