package com.pabloromano.uteambackendtest.api.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pabloromano.uteambackendtest.api.model.Person;
import com.pabloromano.uteambackendtest.api.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class PersonController {
    
    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    
    @GetMapping("/user")
    public Person findPersonById(@RequestParam int id) {
        Optional<Person> person = personService.findPersonById(id);
        if(person.isPresent()) {
            return (Person) person.get();
        }
        return null;
    }

    @GetMapping("/users")
    public ArrayList<Person> listPersons() {
        return personService.listPersons();
    }
    
}
