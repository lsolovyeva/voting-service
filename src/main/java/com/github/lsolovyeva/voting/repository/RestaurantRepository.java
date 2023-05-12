package com.github.lsolovyeva.voting.repository;

import com.github.lsolovyeva.voting.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    List<Restaurant> findAll();

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.dishes d WHERE r.id=:restaurantId AND d.createDate BETWEEN :startDate and :endDate")
    Optional<Restaurant> findByIdWithDishesForToday(@Param("restaurantId") Long restaurantId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.dishes d WHERE r.id=:restaurantId")
    Optional<Restaurant> findByIdWithDishes(@Param("restaurantId") Long restaurantId);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.dishes d WHERE r.id=:restaurantId AND d.name=:name")
    Optional<Restaurant> findByRestaurantIdAndDishName(@Param("restaurantId") Long restaurantId, @Param("name") String name);
}