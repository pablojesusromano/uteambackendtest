package com.pabloromano.uteambackendtest.api.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.pabloromano.uteambackendtest.api.dto.MovieDto;
import com.pabloromano.uteambackendtest.api.dto.PersonDto;
import com.pabloromano.uteambackendtest.api.model.Movie;
import com.pabloromano.uteambackendtest.api.model.Person;
import com.pabloromano.uteambackendtest.api.service.PersonService;
import com.pabloromano.uteambackendtest.api.service.MovieService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/persons")
public class PersonController {
    
    private PersonService personService;
    private MovieService movieService;

    public PersonController(PersonService personService, MovieService movieService) {
        this.personService = personService;
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> findPersonById(@PathVariable int id) {
        Optional<PersonDto> oPerson = personService.findPersonDtoById(id);
        if(oPerson.isPresent()) {
            return ResponseEntity.ok((PersonDto) oPerson.get());
        }
        return null;
    }

    @GetMapping("/findByName")
    public ResponseEntity<ArrayList<PersonDto>> findPersonByName(@RequestParam String name) {
        ArrayList<PersonDto> persons = personService.findPersonByName(name);
        return ResponseEntity.ok(persons);
    }

    @GetMapping("")
    public ResponseEntity<ArrayList<PersonDto>> listPersons() {
        return ResponseEntity.ok(personService.listOrderedPersonsByName());
    }

    @PostMapping("")
    public ResponseEntity<?> storePerson(@RequestBody PersonDto person) {
        if(person.getFavouriteMovies() != null && person.getFavouriteMovies().size() > Person.MAX_MOVIES){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El límite máximo de películas que se pueden almacenar es: " + Person.MAX_MOVIES);
        }
        PersonDto storedPerson = personService.storePerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(storedPerson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable int id, @RequestBody PersonDto person) {
        Optional<PersonDto> oPerson = personService.findPersonDtoById(id);
        if(oPerson.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
        }
        if(person.getFavouriteMovies() != null){
            if(person.getFavouriteMovies().size() > Person.MAX_MOVIES){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El límite máximo de películas que se pueden almacenar es: " + Person.MAX_MOVIES);
            }
            if(oPerson.get().getFavouriteMovies() != null && oPerson.get().getFavouriteMovies().size() == Person.MAX_MOVIES){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La persona ya tiene " + Person.MAX_MOVIES + " películas.");
            }
        }
        PersonDto updatedPerson = personService.updatePerson(id, person);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable int id) {
        Optional<PersonDto> oPerson = personService.findPersonDtoById(id);
        if(oPerson.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
        }
        ArrayList<PersonDto> personsLeft = personService.deletePerson(id);
        return ResponseEntity.status(HttpStatus.OK).body(personsLeft);
    }

    @GetMapping("/{id}/movies")
    public ResponseEntity<?> listPersonMovies(@PathVariable int id) {
        Optional<Person> oPerson = personService.findPersonById(id);
        if(oPerson.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
        }
        ArrayList<MovieDto> movies = personService.listPersonMovies(id);
        return ResponseEntity.status(HttpStatus.OK).body(movies);
    }

    @PutMapping("/{id}/addMovie")
    public ResponseEntity<?> addMovie(@PathVariable int id, @RequestBody MovieDto movie) {
        Optional<Person> oPerson = personService.findPersonById(id);
        if(oPerson.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
        }
        if(oPerson.get().getFavouriteMovies().size() == Person.MAX_MOVIES){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El límite máximo de películas que se pueden almacenar es: " + Person.MAX_MOVIES);
        }
        PersonDto updatedPerson = personService.addMovie(id, movie);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
    }

    @DeleteMapping("/{id}/deleteMovie/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable int id, @PathVariable int movieId) {
        Optional<Person> oPerson = personService.findPersonById(id);
        if(oPerson.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
        }
        if(oPerson.get().getFavouriteMovies().size() == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La persona no tiene películas favoritas por remover.");
        }
        Optional<Movie> oMovie = movieService.findMovieById(movieId);
        if(oMovie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Película no encontrada.");
        }
        if(!(movieService.getIdArray(oPerson.get().getFavouriteMovies()).contains((Integer) oMovie.get().getId()))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La persona no posee la película entre sus favoritas.");
        }
        PersonDto personDto = personService.deleteMovie(id, movieId);
        return ResponseEntity.status(HttpStatus.OK).body(personDto);
    }
}
