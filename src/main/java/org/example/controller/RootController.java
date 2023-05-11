package org.example.controller;

import org.example.model.Dish;
import org.example.model.Restaurant;
import org.example.service.DishService;
import org.example.service.RestaurantService;
import org.example.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api")
public class RootController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteService voteService;

    @GetMapping
    public String index() {
        return "Welcome to the Restaurant Voting Application!";
    }

    @RequestMapping(value = "/403", method = {GET, POST, PUT, DELETE})
    public String accessDenied() {
        return "Error: Access denied!";
    }

    @GetMapping("/dishes/active")
    public List<Dish> getAllDishesForToday(@RequestParam Long restaurantId) {
        return dishService.getAllDishesForToday(restaurantId);
    }

    @GetMapping("/dishes")
    public List<Dish> getAllDishes(@RequestParam Long restaurantId) {
        return dishService.getAllDishes(restaurantId);
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAll();
    }

    @GetMapping("/votes")
    public ResponseEntity<Integer> getAllVotesFor(@RequestParam Long restaurantId) {
        Integer votes = voteService.getVotesCount(restaurantId);
        return new ResponseEntity<>(votes, HttpStatus.OK);
    }
}
