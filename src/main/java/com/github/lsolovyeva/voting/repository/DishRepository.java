package com.github.lsolovyeva.voting.repository;

import com.github.lsolovyeva.voting.model.Dish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends CrudRepository<Dish, Long> {

}
