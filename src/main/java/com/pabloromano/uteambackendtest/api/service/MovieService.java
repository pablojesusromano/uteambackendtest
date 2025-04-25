package com.pabloromano.uteambackendtest.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pabloromano.uteambackendtest.api.dto.MovieDto;
import com.pabloromano.uteambackendtest.api.mapper.MovieMapper;
import com.pabloromano.uteambackendtest.api.model.Movie;

@Service
public class MovieService {
    private ArrayList<Movie> moviesList;

    public MovieService() {

        ArrayList<Movie> moviesList = new ArrayList<Movie>();
        Movie movie1 = new Movie(1, "Kiki's Delivery Service", "animation");
        Movie movie2 = new Movie(2, "Porco Rosso", "animation");
        Movie movie3 = new Movie(3, "The Lord of the Rings", "fantasy");
        Movie movie4 = new Movie(4, "Pulp Fiction", "action");
        Movie movie5 = new Movie(5, "Spirited Away", "animation");
        moviesList.addAll(Arrays.asList(movie1, movie2, movie3, movie4, movie5));
        this.moviesList = moviesList;
    }

    public ArrayList<Movie> listMovies() {
        return moviesList;
    }

    public Optional<Movie> findMovieById(int id) {
        Optional<Movie> oMovie = Optional.empty();
        for (Movie movie: moviesList) {
            if(id == movie.getId()) {
                oMovie = Optional.of(movie);
                return oMovie;
            }
        }
        return oMovie;
    }

    public Optional<Movie> findMovieByTitle(String title) {
        Optional<Movie> oMovie = Optional.empty();
        for (Movie movie: moviesList) {
            if(title.equals(movie.getTitle())) {
                oMovie = Optional.of(movie);
                return oMovie;
            }
        }
        return oMovie;
    }

    public int incrementMovieId() {
        int prevId = 0;
        for (Movie movie : moviesList) {
            if(prevId < movie.getId()) {
                prevId = movie.getId();
            }
        }
        return prevId + 1;
    }

    public Movie convertDtoToMovie(MovieDto movieDto) {
        Optional<Movie> oMovie = findMovieByTitle(movieDto.getTitle());
        int id;
        if(oMovie.isPresent()) {
            id = oMovie.get().getId();
        } else {
            id = incrementMovieId();
        }
        return MovieMapper.toEntity(id, movieDto);
    }

    public MovieDto convertMovieToDto(Movie movie) {
        return MovieMapper.toDto(movie);
    }

    public ArrayList<Integer> getIdArray(ArrayList<Movie> moviesList) {
        ArrayList<Integer> idArray = new ArrayList<Integer>();
        for (Movie movie : moviesList) {
            idArray.add(movie.getId());
        }
        return idArray;
    }
}
