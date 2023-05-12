package com.github.lsolovyeva.voting.controller;

import com.github.lsolovyeva.voting.model.Restaurant;
import com.github.lsolovyeva.voting.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.github.lsolovyeva.voting.dto.RestaurantRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.github.lsolovyeva.voting.controller.RestaurantController.REST_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    static final String REST_URL = "/api/admin/restaurants";

    private final RestaurantService restaurantService;

    //add new restaurant
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant(restaurantRequest.getName(), null);
        Restaurant newRestaurant = restaurantService.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newRestaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newRestaurant);
    }

    //update restaurant
    @PutMapping(value = "/{restaurant_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RestaurantRequest restaurantRequest,
                       @PathVariable(name = "restaurant_id") Long restaurantId) {
        Restaurant restaurant = new Restaurant(restaurantRequest.getName(), null);
        restaurantService.update(restaurant, restaurantId);
    }
}
