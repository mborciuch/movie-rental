package com.mb.movierental.movie;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "movie",
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<MovieReward> movieRewardSet = new HashSet<>();

    void addReward(MovieReward movieReward){
        this.movieRewardSet.add(movieReward);
        movieReward.setMovie(this);
    }

    void removeReward(MovieReward movieReward) {
        movieReward.setMovie(null);
        this.movieRewardSet.remove(movieReward);
    }

}
