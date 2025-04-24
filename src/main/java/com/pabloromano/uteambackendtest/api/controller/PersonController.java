package com.pabloromano.uteambackendtest.api.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.pabloromano.uteambackendtest.api.model.Person;
import com.pabloromano.uteambackendtest.api.service.PersonService;

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

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findPersonById(@PathVariable int id) {
        Optional<Person> oPerson = personService.findPersonById(id);
        if(oPerson.isPresent()) {
            return ResponseEntity.ok((Person) oPerson.get());
        }
        return null;
    }

    @GetMapping("/findByName")
    public ResponseEntity<ArrayList<Person>> findPersonByName(@RequestParam String name) {
        ArrayList<Person> persons = personService.findPersonByName(name);
        return ResponseEntity.ok(persons);
    }

    @GetMapping("")
    public ResponseEntity<ArrayList<Person>> listPersons() {
        return ResponseEntity.ok(personService.listOrderedPersonsByName());
    }

    @PostMapping("")
    public ResponseEntity<?> storePerson(@RequestBody Person person) {
        if(person.getFavouriteMovies().size() > Person.MAX_MOVIES){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El límite máximo de películas que se pueden almacenar es: " + Person.MAX_MOVIES);
        }
        Person storedPerson = personService.storePerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(storedPerson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable int id, @RequestBody Person person) {
        Optional<Person> oPerson = personService.findPersonById(id);
        if(oPerson.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
        }
        Person updatedPerson = personService.updatePerson(id, person);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable int id) {
        Optional<Person> oPerson = personService.findPersonById(id);
        if(oPerson.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
        }
        ArrayList<Person> personsLeft = personService.deletePerson(id);
        return ResponseEntity.status(HttpStatus.OK).body(personsLeft);
    }
}
