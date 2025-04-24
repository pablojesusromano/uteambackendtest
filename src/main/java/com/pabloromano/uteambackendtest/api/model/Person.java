package com.pabloromano.uteambackendtest.api.model;

import java.util.ArrayList;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String birthdate;
    private Boolean hasInsurance;
    private ArrayList<Movie> favouriteMovies;

    public final static int MAX_MOVIES = 3;

    public Person() {} // Agregado para que Jackson pueda deserializar correctamente al hacer POST

    public Person(int id, String firstName, String lastName, String birthdate, Boolean hasInsurance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.hasInsurance = hasInsurance;
        this.favouriteMovies = new ArrayList<Movie>();
    }

    public Person(int id, String firstName, String lastName, String birthdate, Boolean hasInsurance, ArrayList<Movie> favouriteMovies) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.hasInsurance = hasInsurance;
        this.favouriteMovies = favouriteMovies;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public Boolean getHasInsurance() {
        return hasInsurance;
    }

    public ArrayList<Movie> getFavouriteMovies() {
        return favouriteMovies;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setHasInsurance(Boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public void setFavouriteMovies(ArrayList<Movie> favouriteMovies) {
        this.favouriteMovies = favouriteMovies;
    }

    public String toString() {
        return "Persona: { " + firstName + ", " + lastName + " }";
    }
}