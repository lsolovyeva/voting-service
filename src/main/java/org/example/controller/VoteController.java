package org.example.controller;

import org.example.model.User;
import org.example.model.Vote;
import org.example.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.example.controller.VoteController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String REST_URL = "/api/user/votes";

    @Autowired
    private VoteService voteService;

    //add vote for a restaurant
    @PostMapping()
    @Secured({"ROLE_USER"})
    public ResponseEntity<Vote> create(@RequestParam Long restaurantId, @AuthenticationPrincipal User authUser) {
        Vote newVote = voteService.addVote(authUser.getId(), restaurantId, LocalDate.now());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newVote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newVote);
    }

    //change vote for a restaurant
    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_USER"})
    public void update(@RequestParam Long restaurantId, @AuthenticationPrincipal User authUser) {
        voteService.updateVote(authUser.getId(), restaurantId, LocalDate.now(), LocalTime.now());
    }

    @GetMapping()
    public Vote getVoteForToday(@AuthenticationPrincipal User authUser) {
        return voteService.getVoteForToday(authUser.getId());
    }
}
