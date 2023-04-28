package org.example.service;

import org.example.repository.RestaurantRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.example.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        restaurantService = new RestaurantService(restaurantRepository, userRepository);
    }

    @Test
    void save() {
        when(userRepository.findById(ADMIN_ID)).thenReturn(Optional.of(testAdmin));
        when(restaurantRepository.save(any())).thenReturn(testRestaurant);
        assertNotNull(restaurantService.save(testRestaurant));
    }

    @Test
    void update() {
        when(userRepository.findById(ADMIN_ID)).thenReturn(Optional.of(testAdmin));
        when(restaurantRepository.findById(any())).thenReturn(Optional.of(testRestaurant));
        testRestaurant.setName("newRestaurantName");
        assertTrue(restaurantService.update(testRestaurant, RESTAURANT_ID));
    }

    @Test
    void getAll() {
        when(restaurantRepository.findAll()).thenReturn(Collections.singletonList(testRestaurant));
        assertEquals(1, restaurantService.getAll().size());
    }
}