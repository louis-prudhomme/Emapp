package fr.efrei.se.emapp.common.model;

/**
 * serves as a common ground for the json serialization in the rest api ↔ web app communication
 * represents credentials
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
