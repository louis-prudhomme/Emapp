package fr.efrei.se.emapp.common.model;

/**
 * serves as a common ground for the json serialization in the rest api ↔ web app communication
 * represents employees
 */
public class EmployeeTranscript {
    private int id;
    private String firstName, lastName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
