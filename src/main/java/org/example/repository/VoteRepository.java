package org.example.repository;

import org.example.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends CrudRepository<Vote, Long> {

    List<Vote> findAll();

    Vote findByUserId(Long userId);

    Vote findByUserIdAndRestaurantId(Long userId, Long restaurantId);

    @Query("SELECT v FROM Vote v WHERE v.id=:userId AND v.voteDate=:todayDate")
    Optional<Vote> findByUserIdForToday(@Param("userId") Long userId, @Param("todayDate") LocalDate todayDate);

    @Query("SELECT count(v) FROM Vote v WHERE v.restaurant.id = :restaurantId")
    int getCount(Long restaurantId);
}
