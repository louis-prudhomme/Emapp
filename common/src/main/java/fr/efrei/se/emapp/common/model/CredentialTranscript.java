package fr.efrei.se.emapp.common.model;

/**
 * This class serves as common ground for the web application to REST API communication
 * Specifically, this class represents Credentials to allow their serialization
 */
public class CredentialTranscript {
    private int id;
    private String login, password;
    private boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
