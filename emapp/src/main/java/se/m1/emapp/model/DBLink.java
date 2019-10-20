package se.m1.emapp.model;

import se.m1.emapp.model.exception.DriverNotFoundException;
import se.m1.emapp.model.exception.WrongPreparedQueryParemeterCountException;

import java.sql.*;
import java.util.ArrayList;

public class DBLink {
    private static final String DEFAULT_DRIVER = "com.mysql.jdbc.Driver";

    private String databaseUrl, databaseUsername, databasePassword;

    private Connection connection;

    public static DBLink getNewInstance(String databaseUrl, String databaseUsername, String databasePassword) throws DriverNotFoundException {
        registerDriver();
        return new DBLink(databaseUrl, databaseUsername, databasePassword);
    }

    private DBLink(String databaseUrl, String databaseUsername, String databasePassword) {
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.databaseUrl = databaseUrl;
    }

    public void connect() throws SQLException {
        this.connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
    }

    public void close() throws SQLException {
        this.connection.close();
    }

    public PreparedQuery prepareQuery(String query, ArrayList<PreparedStatementTypes> expectedParameterTypes) throws SQLException, WrongPreparedQueryParemeterCountException {
        if(!doesQueryMatchesExpectedParameters(query, expectedParameterTypes)){
            throw new WrongPreparedQueryParemeterCountException();
        }
        return new PreparedQuery(connection.prepareStatement(query), expectedParameterTypes);
    }

    private static void registerDriver() throws DriverNotFoundException {
        try {
            Class.forName(DEFAULT_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DriverNotFoundException(e);
        }
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    private boolean doesQueryMatchesExpectedParameters(String query, ArrayList<PreparedStatementTypes> preparedStatementTypes) {
        return preparedStatementTypes.size() != query.chars().filter(shar -> shar == '?').count();
    }
}
