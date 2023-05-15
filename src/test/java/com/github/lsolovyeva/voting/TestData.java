package com.github.lsolovyeva.voting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lsolovyeva.voting.model.*;
import com.github.lsolovyeva.voting.dto.DishRequest;
import com.github.lsolovyeva.voting.dto.RestaurantRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static java.time.LocalDate.now;

public class TestData {
    public static final long USER_ID = 1;
    public static final long ADMIN_ID = 2;
    public static final long RESTAURANT_ID = 1;
    public static final long DISH_ID = 1;
    public static final long VOTE_ID = 1;

    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final User testUser = new User(USER_MAIL, "User", "Last", "password", Collections.singleton(Role.USER));
    public static final User testAdmin = new User(ADMIN_MAIL, "Admin", "Last", "admin", Collections.singleton(Role.ADMIN));

    public static final DishRequest dishRequest = new DishRequest("pizza", new BigDecimal("50.5"));
    public static final RestaurantRequest restaurantRequest = new RestaurantRequest("testRestaurantName");

    public static final Dish testDish = new Dish("testDish", new BigDecimal("50.5"), LocalDateTime.now(), null);
    public static final Restaurant testRestaurant = new Restaurant("testRestaurantName", null);
    public static final Vote testVote = new Vote(testUser, testRestaurant, now());

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
