package com.pabloromano.uteambackendtest.api.dto;

import java.util.ArrayList;

public class PersonDto {
    private int id;
    private String firstName;
    private String lastName;
    private String birthdate;
    private Boolean hasInsurance;
    private ArrayList<MovieDto> favouriteMovies;

    public PersonDto() {} // Agregado para que Jackson pueda deserializar correctamente al hacer POST

    public PersonDto(int id, String firstName, String lastName, String birthdate, Boolean hasInsurance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.hasInsurance = hasInsurance;
        this.favouriteMovies = new ArrayList<MovieDto>();
    }

    public PersonDto(int id, String firstName, String lastName, String birthdate, Boolean hasInsurance, ArrayList<MovieDto> favouriteMovies) {
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

    public ArrayList<MovieDto> getFavouriteMovies() {
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

    public void setFavouriteMovies(ArrayList<MovieDto> favouriteMovies) {
        this.favouriteMovies = favouriteMovies;
    }

    public String toString() {
        String message = "Persona: { " + firstName + ", " + lastName + ", ";
        message += "[ ";
        for (MovieDto movie : favouriteMovies) {
            message += "{ ";
            String movieTitle = movie.getTitle();
            message += movieTitle + " } ";
        }
        return message + "]}";
    }
}