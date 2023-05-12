package com.github.lsolovyeva.voting.service;

import com.github.lsolovyeva.voting.TestData;
import com.github.lsolovyeva.voting.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setup() {
        restaurantService = new RestaurantService(restaurantRepository);
    }

    @Test
    void save() {
        when(restaurantRepository.save(any())).thenReturn(TestData.testRestaurant);
        assertNotNull(restaurantService.save(TestData.testRestaurant));
    }

    @Test
    void update() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.of(TestData.testRestaurant));
        TestData.testRestaurant.setName("newRestaurantName");
        assertTrue(restaurantService.update(TestData.testRestaurant, TestData.RESTAURANT_ID));
    }

    @Test
    void getAll() {
        when(restaurantRepository.findAll()).thenReturn(Collections.singletonList(TestData.testRestaurant));
        assertEquals(1, restaurantService.getAll().size());
    }
}
