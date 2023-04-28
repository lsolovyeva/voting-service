package org.example.controller;

import org.example.dto.DishRequest;
import org.example.model.Dish;
import org.example.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/api/admin/menus", produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {

    @Autowired
    private DishService dishService;

    //change menu - add new dish
    @PostMapping(value = "/add/{restaurant_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Dish> create(@RequestBody DishRequest dishRequest, @PathVariable(name = "restaurant_id") Long restaurantId) {
        Dish dish = Dish.builder().name(dishRequest.getName()).price(dishRequest.getPrice()).build();
        return new ResponseEntity<>(dishService.addNewDish(dish, restaurantId), CREATED);
    }

    //change menu - update dish
    @PutMapping(value = "/update/{dish_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    public void update(@RequestBody DishRequest dishRequest, @PathVariable(name = "dish_id") Long dishId) {
        Dish dish = Dish.builder().name(dishRequest.getName()).price(dishRequest.getPrice()).build();
        dishService.updateDish(dish, dishId);
    }

    //change menu - (remove dish with audit) = disable/enable
    @PutMapping("/disable/{dish_id}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    public void toggle(@PathVariable(name = "dish_id") Long dishId) {
        dishService.changeDishActivation(dishId);
    }
}
