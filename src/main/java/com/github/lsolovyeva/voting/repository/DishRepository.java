package com.github.lsolovyeva.voting.repository;

import com.github.lsolovyeva.voting.model.Dish;
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
public interface DishRepository extends CrudRepository<Dish, Long> {

    List<Dish> findAllByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.createDate BETWEEN :startDate and :endDate")
    List<Dish> findByIdWithDishesForToday(@Param("restaurantId") Long restaurantId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    Optional<Dish> findByRestaurantIdAndName(Long restaurantId, String name);
}
