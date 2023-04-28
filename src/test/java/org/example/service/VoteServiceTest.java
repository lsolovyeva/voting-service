package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.repository.RestaurantRepository;
import org.example.repository.UserRepository;
import org.example.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.example.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    private VoteService voteService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        voteService = new VoteService(restaurantRepository, voteRepository, userRepository);
        lenient().when(userRepository.findById(USER_ID)).thenReturn(Optional.of(testUser));
        when(restaurantRepository.findById(any())).thenReturn(Optional.of(testRestaurant));
    }

    @Test
    void processNewVote() {
        when(voteRepository.save(any())).thenReturn(testVote);
        assertNotNull(voteService.processVote(USER_ID, RESTAURANT_ID, new Date()));
    }

    @Test
    void processExistingVoteAndEligibleToChange() {
        Date eligibleDate = new Date();
        eligibleDate.setTime(1681970400000L); // 20/04/2023, 9:00
        when(voteRepository.findByUserIdAndRestaurantId(USER_ID, RESTAURANT_ID)).thenReturn(testVote);
        when(voteRepository.save(any())).thenReturn(testVote);
        assertNotNull(voteService.processVote(USER_ID, RESTAURANT_ID, eligibleDate));
    }

    @Test
    void processExistingVoteAndNotEligibleToChange() {
        Date notEligibleDate = new Date();
        notEligibleDate.setTime(1682010000000L); // 20/04/2023, 20:00
        when(voteRepository.findByUserIdAndRestaurantId(USER_ID, RESTAURANT_ID)).thenReturn(testVote);
        assertThrows(UnsupportedOperationException.class, () -> voteService.processVote(USER_ID, RESTAURANT_ID, notEligibleDate));
    }

    @Test
    void testGetVotesCount() {
        when(voteRepository.getCount(any())).thenReturn(1);
        assertEquals(1, voteService.getVotesCount(RESTAURANT_ID));
    }

    @Test
    void testNotGetVotesCountWhenRestaurantNotExist() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> voteService.getVotesCount(RESTAURANT_ID));
    }
}
