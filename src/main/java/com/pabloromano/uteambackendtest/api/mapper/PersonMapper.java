package com.pabloromano.uteambackendtest.api.mapper;

import java.util.ArrayList;

import com.pabloromano.uteambackendtest.api.dto.MovieDto;
import com.pabloromano.uteambackendtest.api.dto.PersonDto;
import com.pabloromano.uteambackendtest.api.model.Movie;
import com.pabloromano.uteambackendtest.api.model.Person;

public class PersonMapper {
    public static Person toEntity(PersonDto personDto, ArrayList<Movie> favouriteMovies) {
        return new Person(
            personDto.getId(),
            personDto.getFirstName(),
            personDto.getLastName(),
            personDto.getBirthdate(),
            personDto.getHasInsurance(),
            favouriteMovies
        );
    }

    public static PersonDto toDto(Person person, ArrayList<MovieDto> favouriteMovies) {
        return new PersonDto(
            person.getId(),
            person.getFirstName(),
            person.getLastName(),
            person.getBirthdate(),
            person.getHasInsurance(),
            favouriteMovies
        );
    }
}
