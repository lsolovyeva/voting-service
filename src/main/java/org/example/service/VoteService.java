package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Restaurant;
import org.example.model.User;
import org.example.model.Vote;
import org.example.repository.RestaurantRepository;
import org.example.repository.UserRepository;
import org.example.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class VoteService {

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public VoteService(RestaurantRepository restaurantRepository, VoteRepository voteRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Vote processVote(Long userId, Long restaurantId, Date newVoteDate) {
        User user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("User with id=" + userId + " not found."));
        //Long votedRestaurantId = voteRepository.findRestaurantIdByUserId(user.getId());
        // if (votedRestaurantId != null && !Objects.equals(restaurantId, voteRepository.findRestaurantIdByUserId(user.getId()))) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new EntityNotFoundException("Restaurant with id=" + restaurantId + " not found."));
        Vote vote = voteRepository.findByUserIdAndRestaurantId(user.getId(), restaurantId);

        /*if (oUser.isEmpty() || !(oUser.get().getRoles().contains(USER)) || oRestaurant.isEmpty()) {
            log.error("Unable to process vote: user must be authorized and restaurant must exist.");
            return null;
        }*/

        if (vote == null) {
            vote = new Vote();
            vote.setUser(user);
            vote.setRestaurant(restaurant);
            vote.setVoteDate(newVoteDate);
            return voteRepository.save(vote);
        }

        if (isEligibleToChange(newVoteDate)) {
            vote.setUser(user);
            vote.setRestaurant(restaurant);
            vote.setVoteDate(newVoteDate);
            return voteRepository.save(vote);
        } else {
            throw new UnsupportedOperationException("Unable to process vote for user id=" + user.getId() +
                    " and restaurant id=" + restaurantId + " : voting period has been closed.");
        }
        //return null;
    }

    public Integer getVotesCount(Long restaurantId) {
        if (restaurantRepository.findById(restaurantId).isEmpty()) {
            throw new EntityNotFoundException("Restaurant with id=" + restaurantId + " not found.");
        }
        return voteRepository.getCount(restaurantId);
    }

    @Transactional
    public Vote processVote(Long restaurantId, Date voteDate) {
        return null;
    }

    private static boolean isEligibleToChange(Date newVoteDate) {
        LocalTime voteTime = LocalTime.ofInstant(newVoteDate.toInstant(), ZoneId.systemDefault());
        LocalTime compareTime = LocalTime.of(11, 0);
        return voteTime.isBefore(compareTime);
    }
}
