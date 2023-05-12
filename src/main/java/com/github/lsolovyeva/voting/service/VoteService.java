package com.github.lsolovyeva.voting.service;

import com.github.lsolovyeva.voting.model.Restaurant;
import com.github.lsolovyeva.voting.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.lsolovyeva.voting.model.Vote;
import com.github.lsolovyeva.voting.repository.RestaurantRepository;
import com.github.lsolovyeva.voting.repository.UserRepository;
import com.github.lsolovyeva.voting.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteService {

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

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
        Vote vote = voteRepository.findByUserId(userId);
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
        return voteRepository.findByUserIdForToday(userId, LocalDate.now())
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
