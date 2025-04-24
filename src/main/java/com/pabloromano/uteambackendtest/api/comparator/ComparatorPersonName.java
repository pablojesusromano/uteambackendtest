package com.pabloromano.uteambackendtest.api.comparator;

import java.util.Comparator;

import com.pabloromano.uteambackendtest.api.model.Person;

public class ComparatorPersonName implements Comparator<Person> {
    
    @Override
    public int compare(Person person1, Person person2) {
        return (person1.getLastName() + person1.getFirstName()).compareTo(person2.getLastName() + person2.getFirstName());
    }
}
