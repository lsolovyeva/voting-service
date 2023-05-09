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

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/api/admin/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    //add new restaurant
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Restaurant> create(@Valid @RequestBody RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant(restaurantRequest.getName(), null);
        return new ResponseEntity<>(restaurantService.save(restaurant), CREATED);
    }

    //update restaurant
    @PutMapping(value = "/update/{restaurant_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    public void update(@Valid @RequestBody RestaurantRequest restaurantRequest, @PathVariable(name = "restaurant_id") Long restaurantId) {
        Restaurant restaurant = new Restaurant(restaurantRequest.getName(), null);
        restaurantService.update(restaurant, restaurantId);
    }
}
