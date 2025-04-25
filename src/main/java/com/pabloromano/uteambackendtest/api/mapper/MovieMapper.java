package com.pabloromano.uteambackendtest.api.mapper;

import com.pabloromano.uteambackendtest.api.dto.MovieDto;
import com.pabloromano.uteambackendtest.api.model.Movie;

public class MovieMapper {
    public static Movie toEntity(int id, MovieDto movieDto) {
        return new Movie(
            id,
            movieDto.getTitle(),
            movieDto.getGenre()
        );
    }

    public static MovieDto toDto(Movie movie) {
        return new MovieDto(
            movie.getTitle(),
            movie.getGenre()
        );
    }
}
