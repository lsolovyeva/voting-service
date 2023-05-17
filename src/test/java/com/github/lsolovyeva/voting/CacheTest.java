package com.github.lsolovyeva.voting;

import com.github.lsolovyeva.voting.config.AppConfig;
import com.github.lsolovyeva.voting.model.Dish;
import com.github.lsolovyeva.voting.model.Restaurant;
import com.github.lsolovyeva.voting.service.CacheInvalidator;
import com.github.lsolovyeva.voting.service.DishService;
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

import static com.github.lsolovyeva.voting.TestData.RESTAURANT_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = VotingServiceApplication.class)
class CacheTest {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishService dishService;

    @Autowired
    private CacheInvalidator cacheInvalidator;

    @AfterEach
    void tearDown() {
        Objects.requireNonNull(cacheManager.getCache(AppConfig.RESTAURANTS_CACHE)).invalidate();
    }

    @Test
    void getCachedRestaurants() {
        restaurantService.getAll();
        ArrayList<Restaurant> cachedItems = (ArrayList<Restaurant>) cacheManager.getCache(AppConfig.RESTAURANTS_CACHE)
                .get(SimpleKey.EMPTY).get();
        assertEquals(2, cachedItems.size());
    }

    @Test
    void clearDishCache() {
        dishService.getAllForToday(RESTAURANT_ID); // first request to cache dishes
        ArrayList<Dish> cachedItems = (ArrayList<Dish>) cacheManager.getCache(AppConfig.DISHES_CACHE).get(RESTAURANT_ID).get();
        assertEquals(2, cachedItems.size());
        cacheInvalidator.clearCache();
        assertNull(cacheManager.getCache(AppConfig.DISHES_CACHE).get(RESTAURANT_ID));
    }
}
