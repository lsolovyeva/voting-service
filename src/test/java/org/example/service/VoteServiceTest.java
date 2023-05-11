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

import java.time.LocalDate;
import java.time.LocalTime;
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
        assertNotNull(voteService.addVote(USER_ID, RESTAURANT_ID, LocalDate.now()));
    }

    @Test
    void processExistingVoteAndEligibleToChange() {
        LocalTime eligibleTime = LocalTime.of(9, 0, 0, 0);
        when(voteRepository.findByUserIdAndRestaurantId(USER_ID, RESTAURANT_ID)).thenReturn(testVote);
        when(voteRepository.save(any())).thenReturn(testVote);
        assertDoesNotThrow(() -> voteService.updateVote(USER_ID, RESTAURANT_ID, LocalDate.now(), eligibleTime));
    }

    @Test
    void processExistingVoteAndNotEligibleToChange() {
        LocalTime notEligibleDate = LocalTime.of(20, 0, 0, 0);
        LocalDate voteDate = LocalDate.now();
        when(voteRepository.findByUserIdAndRestaurantId(USER_ID, RESTAURANT_ID)).thenReturn(testVote);
        assertThrows(UnsupportedOperationException.class,
                () -> voteService.updateVote(USER_ID, RESTAURANT_ID, voteDate, notEligibleDate));
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
