package com.pabloromano.uteambackendtest.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pabloromano.uteambackendtest.api.model.Movie;
import com.pabloromano.uteambackendtest.api.model.Person;

@Service
public class PersonService {

    private ArrayList<Person> personList;

    public PersonService() {
        ArrayList<Movie> movieList1 = new ArrayList<Movie>();
        ArrayList<Movie> movieList2 = new ArrayList<Movie>();
        Movie movie1 = new Movie("Kiki's Delivery Service", "animation");
        Movie movie2 = new Movie("Porco Rosso", "animation");
        Movie movie3 = new Movie("The Lord of the Rings", "fantasy");
        Movie movie4 = new Movie("Pulp Fiction", "action");
        Movie movie5 = new Movie("Spirited Away", "animation");
        movieList1.addAll(Arrays.asList(movie1, movie2));
        movieList2.addAll(Arrays.asList(movie3, movie4, movie5));

        ArrayList<Person> personList = new ArrayList<Person>();
        Person person1 = new Person(1, "Juan", "Perez", "1990-12-12", true, movieList1);
        Person person2 = new Person(2, "Pablo", "Lamberti", "1987-04-03", false, movieList2);
        Person person3 = new Person(3, "Nombre", "Apellido", "1982-01-05", false);
        personList.addAll(Arrays.asList(person1, person2, person3));

        this.personList = personList;
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

    public ArrayList<Person> findPersonByName(String name) {
        ArrayList<Person> matchedPersonsList = new ArrayList<Person>();
        for (Person person: personList) {
            if(name.equals(person.getFirstName()) || name.equals(person.getLastName())) {
                matchedPersonsList.add(person);
            }
        }
        return matchedPersonsList;
    }

    public ArrayList<Person> listPersons() {
        ArrayList<Person> personList = new ArrayList<>();
        personList = this.personList;
        return personList;
    }
    
}
