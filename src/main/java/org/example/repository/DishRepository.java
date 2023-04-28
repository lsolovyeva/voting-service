package org.example.repository;

import org.example.model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends CrudRepository<Dish, Long> {

    //List<Dish> findByRestaurantId(Long restaurantId);

}
