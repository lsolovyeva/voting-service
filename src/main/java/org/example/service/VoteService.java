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

import java.time.LocalDate;
import java.time.LocalTime;

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
    public Vote addVote(Long userId, Long restaurantId, LocalDate newVoteDate) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id=" + restaurantId + " not found."));
        Vote vote = voteRepository.findByUserIdAndRestaurantId(userId, restaurantId);

        if (vote != null) {
            log.info("Error: cannot create vote as it already exists");
            return null;
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id=" + userId + " not found."));
        vote = new Vote();
        vote.setUser(user);
        vote.setRestaurant(restaurant);
        vote.setVoteDate(newVoteDate);
        return voteRepository.save(vote);
    }

    @Transactional
    public void updateVote(Long userId, Long restaurantId, LocalDate newVoteDate, LocalTime newVoteTime) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id=" + restaurantId + " not found."));
        Vote vote = voteRepository.findByUserIdAndRestaurantId(userId, restaurantId);
        if (vote == null) {
            log.info("Error: cannot update vote as it does not exist");
            return;
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id=" + userId + " not found."));
        if (isEligibleToChange(newVoteTime)) {
            vote.setUser(user);
            vote.setRestaurant(restaurant);
            vote.setVoteDate(newVoteDate);
            voteRepository.save(vote);
        } else {
            throw new UnsupportedOperationException("Unable to process vote for user id=" + userId +
                    " and restaurant id=" + restaurantId + " : voting period has been closed.");
        }
    }

    public Vote getVoteForToday(Long userId) {
        return voteRepository.findByUserIdForToday(userId, LocalDate.now().atTime(LocalTime.MIDNIGHT), LocalDate.now().atTime(LocalTime.MAX))
                .orElseThrow(() -> new EntityNotFoundException("Vote for user with id=" + userId + " not found."));

    }

    public Integer getVotesCount(Long restaurantId) {
        if (restaurantRepository.findById(restaurantId).isEmpty()) {
            throw new EntityNotFoundException("Restaurant with id=" + restaurantId + " not found.");
        }
        return voteRepository.getCount(restaurantId);
    }

    private static boolean isEligibleToChange(LocalTime newVoteDate) {
        LocalTime compareTime = LocalTime.of(11, 0);
        return newVoteDate.isBefore(compareTime);
    }
}
