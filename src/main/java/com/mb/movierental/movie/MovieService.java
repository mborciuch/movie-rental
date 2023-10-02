package com.mb.movierental.movie;

public interface MovieService {

    void saveMovie(Movie movie);

    void addReward(long movieId, MovieReward movieReward);

    Movie find(Long id);
}
