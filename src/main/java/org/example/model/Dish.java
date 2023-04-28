package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@Builder
//@ToString(exclude = "restaurant")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dish", schema = "votes", catalog = "voting")
public class Dish extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String name;

    @Column(name = "price", nullable = false)
    //@Range(min = 1, max = 1000)
    private BigDecimal price;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled;

    //@ManyToOne() //fetch = FetchType.EAGER
    //@JoinColumn(name = "restaurant_id")
    //private Restaurant restaurant;
}