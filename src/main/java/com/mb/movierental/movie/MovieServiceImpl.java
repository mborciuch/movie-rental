package com.mb.movierental.movie;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    @Override
    @Transactional
    public void saveMovie(Movie movie) {
        this.movieRepository.save(movie);
    }

    @Override
    @Transactional
    public void addReward(long movieId, MovieReward movieReward) {
        Movie movie = this.movieRepository.findById(movieId).orElseThrow(RuntimeException::new);
        movie.addReward(movieReward);
    }

    @Override
    public Movie find(Long id) {
        return this.movieRepository.findById(id).orElseThrow(() -> new IllegalStateException("Movie  does not exist"));
    }
}
