package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.DishRequest;
import org.example.model.Dish;
import org.example.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static java.time.LocalDateTime.now;
import static org.example.controller.DishController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    static final String REST_URL = "/api/admin/dishes";

    @Autowired
    private DishService dishService;

    //change menu - add new dish
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Dish> create(@Valid @RequestBody DishRequest dishRequest, @RequestParam Long restaurantId) {
        Dish dish = new Dish(dishRequest.getName(), dishRequest.getPrice(), now());
        Dish newDish = dishService.addNewDish(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newDish.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newDish);
    }

    //change menu - update dish
    @PutMapping(value = "/{dish_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN"})
    public void update(@Valid @RequestBody DishRequest dishRequest, @PathVariable(name = "dish_id") Long dishId) {
        Dish dish = new Dish(dishRequest.getName(), dishRequest.getPrice(), null);
        dishService.updateDish(dish, dishId);
    }
}
