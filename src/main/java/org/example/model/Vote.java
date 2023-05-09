package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "vote", schema = "votes", catalog = "voting",
        uniqueConstraints = {@UniqueConstraint(name = "user_unique_vote_date_idx", columnNames = {"user_id", "vote_date"})})
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    @Column(name = "user_id")
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "vote_date", nullable = false)
    @NotNull
    private LocalDate voteDate;

}
