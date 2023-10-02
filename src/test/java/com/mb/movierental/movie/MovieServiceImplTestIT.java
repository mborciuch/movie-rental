package com.mb.movierental.movie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MovieServiceImplTestIT {

    @Autowired
    MovieService movieService;

    @Test
    void add_movie_thenSuccess(){
        Movie movie = new Movie();
        movie.setName("Ben Hur");

        movieService.saveMovie(movie);

        assertNotNull(movie.getId());
    }

    @Test
    @Transactional
    void addRewardToMovieThenSuccess(){
        Movie movie = new Movie();
        movie.setName("Ben Hur");
        movieService.saveMovie(movie);
        Long id = movie.getId();

        MovieReward movieReward = new MovieReward();

        movieService.addReward(id, movieReward);
        movie = movieService.find(id);

        assertFalse(movie.getMovieRewardSet().isEmpty());
    }
}
