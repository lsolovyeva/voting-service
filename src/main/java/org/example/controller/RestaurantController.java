package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.RestaurantRequest;
import org.example.model.Restaurant;
import org.example.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.example.controller.RestaurantController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    static final String REST_URL = "/api/admin/restaurants";

    @Autowired
    private RestaurantService restaurantService;

    //add new restaurant
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_ADMIN"})
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
    @Secured({"ROLE_ADMIN"})
    public void update(@Valid @RequestBody RestaurantRequest restaurantRequest,
                       @PathVariable(name = "restaurant_id") Long restaurantId) {
        Restaurant restaurant = new Restaurant(restaurantRequest.getName(), null);
        restaurantService.update(restaurant, restaurantId);
    }
}
