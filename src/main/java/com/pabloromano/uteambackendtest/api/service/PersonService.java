package com.pabloromano.uteambackendtest.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pabloromano.uteambackendtest.api.comparator.ComparatorPersonName;
import com.pabloromano.uteambackendtest.api.dto.MovieDto;
import com.pabloromano.uteambackendtest.api.dto.PersonDto;
import com.pabloromano.uteambackendtest.api.mapper.MovieMapper;
import com.pabloromano.uteambackendtest.api.mapper.PersonMapper;
import com.pabloromano.uteambackendtest.api.model.Person;
import com.pabloromano.uteambackendtest.api.model.Movie;

@Service
public class PersonService {

    private MovieService movieService;
    private ArrayList<Person> personList;

    public PersonService(MovieService movieService) {
        this.movieService = movieService;
        ArrayList<Movie> movieList1 = new ArrayList<Movie>();
        ArrayList<Movie> movieList2 = new ArrayList<Movie>();
        Movie movie1 = movieService.findMovieById(1).orElse(null);
        Movie movie2 = movieService.findMovieById(2).orElse(null);
        Movie movie3 = movieService.findMovieById(3).orElse(null);
        Movie movie4 = movieService.findMovieById(4).orElse(null);
        Movie movie5 = movieService.findMovieById(5).orElse(null);

        movieList1.addAll(Arrays.asList(movie1, movie2));
        movieList2.addAll(Arrays.asList(movie3, movie4, movie5));

        ArrayList<Person> personList = new ArrayList<Person>();
        Person person1 = new Person(1, "Juan", "Perez", "1990-12-12", true, movieList1);
        Person person2 = new Person(2, "Juan", "Dominguez", "1992-11-13", true);
        Person person3 = new Person(3, "Pablo", "Lamberti", "1987-04-03", false, movieList2);
        Person person4 = new Person(4, "Nombre", "Apellido", "1982-01-05", false);
        personList.addAll(Arrays.asList(person1, person2, person3, person4));

        this.personList = personList;
    }

    public PersonDto storePerson(PersonDto personDto) {
        Person person = convertDtoToPerson(personDto);
        ArrayList<Movie> movies = new ArrayList<Movie>();
        for (Movie movie : person.getFavouriteMovies()) {
            Movie foundMovie = movieService.findMovieByTitle(movie.getTitle()).get();
            movies.add(foundMovie);
        }
        Person newPerson = new Person(incrementPersonId(), person.getFirstName(), person.getLastName(), person.getBirthdate(), person.getHasInsurance(), movies);
        this.personList.add(newPerson);
        PersonDto addedPerson = convertPersonToDto(newPerson);
        return addedPerson;
    }

    public PersonDto updatePerson(int id, PersonDto personDto) {
        Optional<Person> oPerson = this.findPersonById(id);
        Person existingPerson = oPerson.get();
        if(personDto.getFirstName() != null) {
            existingPerson.setFirstName(personDto.getFirstName());
        }
        if(personDto.getLastName() != null) {
            existingPerson.setLastName(personDto.getLastName());
        }
        if(personDto.getBirthdate() != null) {
            existingPerson.setBirthdate(personDto.getBirthdate());
        }
        if(personDto.getHasInsurance() != null) {
            existingPerson.setHasInsurance(personDto.getHasInsurance());
        }
        if(personDto.getFavouriteMovies() != null) {
            existingPerson.setFavouriteMovies(convertDtosToFavouriteMovies(personDto.getFavouriteMovies()));
        }
        this.personList.set(this.personList.indexOf(oPerson.get()), existingPerson);
        PersonDto updatedPerson = convertPersonToDto(existingPerson);
        System.out.println(updatedPerson.toString());
        return updatedPerson;
    }

    public ArrayList<PersonDto> deletePerson(int id) {
        Optional<Person> oPerson = this.findPersonById(id);
        this.personList.remove(personList.indexOf(oPerson.get()));
        return listOrderedPersonsByName();
    }
    
    public Optional<PersonDto> findPersonDtoById(int id) {
        Optional<PersonDto> oPerson = Optional.empty();
        for (Person person: personList) {
            if(id == person.getId()) {
                oPerson = Optional.of(convertPersonToDto(person));
                return oPerson;
            }
        }
        return oPerson;
    }

    public Optional<Person> findPersonById(int id) {
        Optional<Person> oPerson = Optional.empty();
        for (Person person: personList) {
            if(id == person.getId()) {
                oPerson = Optional.of(person);
                return oPerson;
            }
        }
        return oPerson;
    }

    public ArrayList<PersonDto> findPersonByName(String name) {
        ArrayList<PersonDto> matchedPersonsList = new ArrayList<PersonDto>();
        for (Person person: personList) {
            if(name.equals(person.getFirstName()) || name.equals(person.getLastName())) {
                matchedPersonsList.add(convertPersonToDto(person));
            }
        }
        return matchedPersonsList;
    }

    public ArrayList<PersonDto> listOrderedPersonsByName() {
        ArrayList<Person> orderedPersonList = orderPersonsByName(this.personList);
        ArrayList<PersonDto> personDtoList = new ArrayList<PersonDto>();
        for (Person person : orderedPersonList) {
            personDtoList.add(convertPersonToDto(person));
        }
        return personDtoList;
    }
    
    public static ArrayList<Person> orderPersonsByName(ArrayList<Person> personArrayList) {
        ArrayList<Person> orderedPersonList = personArrayList;
        orderedPersonList.sort(new ComparatorPersonName());
        return orderedPersonList;
    }

    public ArrayList<MovieDto> listPersonMovies(int id) {
        Optional<PersonDto> oPersonDto = this.findPersonDtoById(id);
        Person person = convertDtoToPerson(oPersonDto.get());
        ArrayList<Movie> movies = person.getFavouriteMovies();
        ArrayList<MovieDto> moviesDto = new ArrayList<MovieDto>();
        for (Movie movie : movies) {
            MovieDto movieDto = new MovieDto(movie.getTitle(), movie.getGenre());
            moviesDto.add(movieDto);
        }
        return moviesDto;
    }

    public PersonDto addMovie(int id, MovieDto movieDto) {
        Optional<Person> oPerson = this.findPersonById(id);
        Person existingPerson = (Person) oPerson.get();
        ArrayList<Movie> movies = existingPerson.getFavouriteMovies();
        Optional<Movie> oMovie = movieService.findMovieByTitle(movieDto.getTitle());
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setGenre(movieDto.getGenre());
        if(oMovie.isEmpty()){
            movie.setId(movieService.incrementMovieId());
        } else {
            movie.setId(oMovie.get().getId());
        }
        movies.add(movie);
        existingPerson.setFavouriteMovies(movies);
        this.personList.set(this.personList.indexOf(oPerson.get()), existingPerson);
        PersonDto updatedPerson = convertPersonToDto(existingPerson);
        return updatedPerson;
    }

    public PersonDto deleteMovie(int id, int movieId) {
        Optional<Person> oPerson = this.findPersonById(id);
        Person existingPerson = oPerson.get();
        ArrayList<Movie> movies = existingPerson.getFavouriteMovies();
        for (Movie movie : movies) {
            if(movie.getId() == movieId) {
                movies.remove(movie);
                existingPerson.setFavouriteMovies(movies);
                this.personList.set(this.personList.indexOf(oPerson.get()), existingPerson);
                break;
            }
        }
        PersonDto updatedPerson = convertPersonToDto(existingPerson);
        return updatedPerson;
    }

    public int incrementPersonId() {
        int prevId = 0;
        for (Person person : personList) {
            if(prevId < person.getId()) {
                prevId = person.getId();
            }
        }
        return prevId + 1;
    }

    public PersonDto convertPersonToDto(Person person) {
        ArrayList<Movie> favouriteMovies = person.getFavouriteMovies();
        ArrayList<MovieDto> favouriteMoviesDto = new ArrayList<MovieDto>();
        for (Movie movie : favouriteMovies) {
            MovieDto movieDto = new MovieDto(movie.getTitle(), movie.getGenre());
            favouriteMoviesDto.add(movieDto);
        }
        PersonDto personDto = PersonMapper.toDto(person, favouriteMoviesDto);
        return personDto;
    }

    public Person convertDtoToPerson(PersonDto personDto) {
        ArrayList<MovieDto> favouriteMoviesDto = personDto.getFavouriteMovies();
        ArrayList<Movie> favouriteMovies = new ArrayList<Movie>();
        for (MovieDto movieDto : favouriteMoviesDto) {
            Movie movie = new Movie(movieService.findMovieByTitle(movieDto.getTitle()).get().getId(), movieDto.getTitle(), movieDto.getGenre());
            favouriteMovies.add(movie);
        }
        Person person = PersonMapper.toEntity(personDto, favouriteMovies);
        return person;
    }

    public ArrayList<Movie> convertDtosToFavouriteMovies(ArrayList<MovieDto> favouriteMoviesDto) {
        ArrayList<Movie> favouriteMovies = new ArrayList<Movie>();
        for (MovieDto movieDto : favouriteMoviesDto) {
            Movie movie = MovieMapper.toEntity(movieService.incrementMovieId(), movieDto);
            favouriteMovies.add(movie);
        }
        return favouriteMovies;
    }
}
