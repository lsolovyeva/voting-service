package com.github.lsolovyeva.voting.service;

import com.github.lsolovyeva.voting.TestData;
import com.github.lsolovyeva.voting.exception.ItemMappingException;
import com.github.lsolovyeva.voting.model.Dish;
import jakarta.persistence.EntityNotFoundException;
import com.github.lsolovyeva.voting.repository.DishRepository;
import com.github.lsolovyeva.voting.repository.RestaurantRepository;
import com.github.lsolovyeva.voting.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
        TestData.testDish.setId(1L);
        lenient().when(userRepository.findById(TestData.ADMIN_ID)).thenReturn(Optional.of(TestData.testAdmin));
    }

    @Test
    void addNewDish() {
        when(dishRepository.save(any())).thenReturn(TestData.testDish);
        when(restaurantRepository.findById(TestData.RESTAURANT_ID)).thenReturn(Optional.of(TestData.testRestaurant));
        Dish dish = dishService.add(TestData.testDish, TestData.RESTAURANT_ID);
        Assertions.assertEquals(TestData.testDish.getName(), dish.getName());
    }

    @Test
    void NotAddNewDishWhenAlreadyPresent() {
        when(restaurantRepository.findById(TestData.RESTAURANT_ID)).thenReturn(Optional.of(TestData.testRestaurant));
        when(dishRepository.findByRestaurantIdAndName(any(), any())).thenReturn(Optional.of(TestData.testDish));
        assertThrows(ItemMappingException.class, () -> dishService.add(TestData.testDish, TestData.RESTAURANT_ID));
    }

    @Test
    void NotAddNewDishWhenRestaurantNotFound() {
        when(restaurantRepository.findById(TestData.RESTAURANT_ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> dishService.add(TestData.testDish, TestData.RESTAURANT_ID));
    }

    @Test
    void updateDish() {
        when(dishRepository.findById(any())).thenReturn(Optional.of(TestData.testDish));
        when(dishRepository.save(any())).thenReturn(TestData.testDish);
        assertDoesNotThrow(() -> dishService.update(TestData.testDish, TestData.DISH_ID));
    }

    @Test
    void notUpdateDishWhenNotFound() {
        when(dishRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> dishService.update(TestData.testDish, TestData.DISH_ID));
    }
}
