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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id=" + userId + " not found."));
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id=" + restaurantId + " not found."));
        Vote vote = voteRepository.findByUserIdAndRestaurantId(userId, restaurantId);

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
            throw new UnsupportedOperationException("Unable to process vote for user id=" + userId +
                    " and restaurant id=" + restaurantId + " : voting period has been closed.");
        }
    }

    public Integer getVotesCount(Long restaurantId) {
        if (restaurantRepository.findById(restaurantId).isEmpty()) {
            throw new EntityNotFoundException("Restaurant with id=" + restaurantId + " not found.");
        }
        return voteRepository.getCount(restaurantId);
    }

    private static boolean isEligibleToChange(Date newVoteDate) {
        LocalTime voteTime = LocalTime.ofInstant(newVoteDate.toInstant(), ZoneId.systemDefault());
        LocalTime compareTime = LocalTime.of(11, 0);
        return voteTime.isBefore(compareTime);
    }
}
