package org.example.controller;

import org.example.model.User;
import org.example.model.Vote;
import org.example.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    @Autowired
    private VoteService voteService;

    //vote for a restaurant
    @PostMapping("/vote/{restaurant_id}")
    @ResponseStatus(CREATED)
    @Secured({"ROLE_USER"})
    public ResponseEntity<Vote> vote(@PathVariable(name = "restaurant_id") Long restaurantId, @AuthenticationPrincipal User authUser) {
        return new ResponseEntity<>(voteService.processVote(authUser.getId(), restaurantId, new Date()), CREATED);
    }
}
