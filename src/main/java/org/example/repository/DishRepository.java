package org.example.repository;

import org.example.model.Dish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends CrudRepository<Dish, Long> {

}
