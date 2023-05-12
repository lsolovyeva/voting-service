package com.github.lsolovyeva.voting.service;

import com.github.lsolovyeva.voting.TestData;
import jakarta.persistence.EntityNotFoundException;
import com.github.lsolovyeva.voting.repository.RestaurantRepository;
import com.github.lsolovyeva.voting.repository.UserRepository;
import com.github.lsolovyeva.voting.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

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
        lenient().when(userRepository.findById(TestData.USER_ID)).thenReturn(Optional.of(TestData.testUser));
        when(restaurantRepository.findById(any())).thenReturn(Optional.of(TestData.testRestaurant));
    }

    @Test
    void processNewVote() {
        when(voteRepository.save(any())).thenReturn(TestData.testVote);
        assertNotNull(voteService.addVote(TestData.USER_ID, TestData.RESTAURANT_ID, LocalDate.now()));
    }

    @Test
    void processExistingVoteAndEligibleToChange() {
        LocalTime eligibleTime = LocalTime.of(9, 0, 0, 0);
        when(voteRepository.findByUserId(TestData.USER_ID)).thenReturn(TestData.testVote);
        when(voteRepository.save(any())).thenReturn(TestData.testVote);
        assertDoesNotThrow(() -> voteService.updateVote(TestData.USER_ID, TestData.RESTAURANT_ID, LocalDate.now(), eligibleTime));
    }

    @Test
    void processExistingVoteAndNotEligibleToChange() {
        LocalTime notEligibleDate = LocalTime.of(20, 0, 0, 0);
        LocalDate voteDate = LocalDate.now();
        when(voteRepository.findByUserId(TestData.USER_ID)).thenReturn(TestData.testVote);
        assertThrows(UnsupportedOperationException.class,
                () -> voteService.updateVote(TestData.USER_ID, TestData.RESTAURANT_ID, voteDate, notEligibleDate));
    }

    @Test
    void testGetVotesCount() {
        when(voteRepository.getCount(any())).thenReturn(1);
        assertEquals(1, voteService.getVotesCount(TestData.RESTAURANT_ID));
    }

    @Test
    void testNotGetVotesCountWhenRestaurantNotExist() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> voteService.getVotesCount(TestData.RESTAURANT_ID));
    }
}
