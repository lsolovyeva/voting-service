package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.DishRequest;
import org.example.dto.RestaurantRequest;
import org.example.model.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

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

    public static final Dish testDish = new Dish("testDish", new BigDecimal("50.5"), true);
    public static final Restaurant testRestaurant = new Restaurant("testRestaurantName", null);
    public static final Vote testVote = new Vote(VOTE_ID, testUser, testRestaurant, new Date());

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
