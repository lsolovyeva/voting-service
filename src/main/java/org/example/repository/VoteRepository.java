package org.example.repository;

import org.example.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends CrudRepository<Vote, Long> {

    List<Vote> findAll();

    Vote findByUserIdAndRestaurantId(Long userId, Long restaurantId);

    @Query("SELECT v.restaurant.id FROM Vote v WHERE v.user.id = :userId")
    Long findRestaurantIdByUserId(Long userId);

    @Query("SELECT count(v) FROM Vote v WHERE v.restaurant.id = :restaurantId")
    int getCount(Long restaurantId);
}
