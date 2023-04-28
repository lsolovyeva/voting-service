package org.example.controller;

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
    //@PreAuthorize("hasRole('ROLE_ADMIN')"), @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Restaurant> create(@RequestBody RestaurantRequest restaurantRequest) { // @PathVariable(name = "user_id") Long userId // @AuthenticationPrincipal User authUser
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //System.out.println(authentication.getAuthorities());
        Restaurant restaurant = Restaurant.builder().name(restaurantRequest.getName()).build();
        return new ResponseEntity<>(restaurantService.save(restaurant), CREATED); //authUser.getUser().getId()
        //return restaurantService.save(restaurant, authUser.getId()); //authUser.getUser().getId()
    }

    //update restaurant
    @PutMapping(value = "/update/{restaurant_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    public void update(@RequestBody RestaurantRequest restaurantRequest, @PathVariable(name = "restaurant_id") Long restaurantId) { //@PathVariable(name = "user_id") Long userId,//, @AuthenticationPrincipal User authUser
        Restaurant restaurant = Restaurant.builder().name(restaurantRequest.getName()).build();
        restaurantService.update(restaurant, restaurantId);
    }

    //add when admin is authorized?

}
