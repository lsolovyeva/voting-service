package com.github.lsolovyeva.voting.controller;

import com.github.lsolovyeva.voting.model.Dish;
import com.github.lsolovyeva.voting.model.Restaurant;
import com.github.lsolovyeva.voting.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import com.github.lsolovyeva.voting.service.DishService;
import com.github.lsolovyeva.voting.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootController {

    private final DishService dishService;
    private final RestaurantService restaurantService;
    private final VoteService voteService;

    @GetMapping
    public String index() {
        return "Welcome to the Restaurant Voting Application!";
    }

    @GetMapping("/dishes/active")
    public List<Dish> getAllDishesForToday(@RequestParam Long restaurantId) {
        return dishService.getAllForToday(restaurantId);
    }

    @GetMapping("/dishes")
    public List<Dish> getAllDishes(@RequestParam Long restaurantId) {
        return dishService.getAll(restaurantId);
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
