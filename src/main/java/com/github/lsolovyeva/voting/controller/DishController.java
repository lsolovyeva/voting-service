package com.github.lsolovyeva.voting.controller;

import com.github.lsolovyeva.voting.dto.DishRequest;
import com.github.lsolovyeva.voting.model.Dish;
import com.github.lsolovyeva.voting.service.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static java.time.LocalDateTime.now;
import static com.github.lsolovyeva.voting.controller.DishController.REST_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    static final String REST_URL = "/api/admin/dishes";

    private final DishService dishService;

    //change menu - add new dish
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
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
    public void update(@Valid @RequestBody DishRequest dishRequest, @PathVariable(name = "dish_id") Long dishId) {
        Dish dish = new Dish(dishRequest.getName(), dishRequest.getPrice(), null);
        dishService.updateDish(dish, dishId);
    }
}
