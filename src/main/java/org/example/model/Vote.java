package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "vote", schema = "votes", catalog = "voting")
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    @Column(name = "user_id") //,updatable = false, nullable = false
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")//, referencedColumnName = "id"
    @JsonIgnore
    private User user;

    @OneToOne() //cascade = CascadeType.ALL,orphanRemoval = true
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "vote_date", nullable = false)
    @NotNull
    private Date voteDate;
}
