package com.github.lsolovyeva.voting.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "dish", schema = "votes", catalog = "voting",
        uniqueConstraints = {@UniqueConstraint(name = "restaurant_unique_dish_idx", columnNames = {"restaurant_id", "name"})})
public class Dish extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String name;

    @Column(name = "price", nullable = false)
    //@Range(min = 1, max = 1000)
    private BigDecimal price;

    @Column(name = "create_date", nullable = false)
    @NotNull
    private LocalDateTime createDate;

}
