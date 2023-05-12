package com.github.lsolovyeva.voting;

import com.github.lsolovyeva.voting.config.AppConfig;
import com.github.lsolovyeva.voting.model.Restaurant;
import com.github.lsolovyeva.voting.service.RestaurantService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = VotingServiceApplication.class)
public class CacheTest {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    RestaurantService restaurantService;

    @AfterEach
    void tearDown() {
        Objects.requireNonNull(cacheManager.getCache(AppConfig.RESTAURANTS_CACHE)).invalidate();
    }

    @Test
    void getCachedRestaurants() {
        restaurantService.getAll();
        ArrayList<Restaurant> cachedItems = (ArrayList<Restaurant>) cacheManager.getCache(AppConfig.RESTAURANTS_CACHE).get(SimpleKey.EMPTY).get();
        assertEquals(2, cachedItems.size());
    }
}
