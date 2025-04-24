package com.pabloromano.uteambackendtest.api.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.pabloromano.uteambackendtest.api.model.Person;
import com.pabloromano.uteambackendtest.api.service.PersonService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



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
        return ResponseEntity.ok(personService.listPersons());
    }
    
}
