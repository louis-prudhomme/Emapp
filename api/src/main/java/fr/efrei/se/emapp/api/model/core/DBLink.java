package fr.efrei.se.emapp.api.model.core;

import fr.efrei.se.emapp.api.model.core.exception.dbLink.DBLDriverNotFoundException;
import fr.efrei.se.emapp.api.model.core.exception.dbLink.DBLUnderlyingException;
import fr.efrei.se.emapp.api.model.core.exception.preparedQuery.PQWrongParameterException;

import java.sql.*;
import java.util.ArrayList;

/**
 * represents a connection to a database
 */
public class DBLink {
    /**
     * default driver
     * //todo put that in a proper configuration file (pom ?)
     */
    private static final String DEFAULT_DRIVER = "com.mysql.jdbc.Driver";

    /**
     * connection parameters
     */
    private String databaseUrl, databaseUsername, databasePassword;

    /**
     * java connection object
     */
    private Connection connection;

    /**
     * creates a new dblink instance with the given parameters
     * it also registers the database driver
     * @param databaseUrl url of the db
     * @param databaseUsername user of the db
     * @param databasePassword pwd of the db
     * @return new dblink instance ; it has to be opened manually
     * @throws DBLDriverNotFoundException if the driver is not found
     */
    public static DBLink getNewInstance(String databaseUrl, String databaseUsername, String databasePassword) throws DBLDriverNotFoundException {
        registerDriver();
        return new DBLink(databaseUrl, databaseUsername, databasePassword);
    }

    /**
     * default constructor
     * @param databaseUrl url of the db
     * @param databaseUsername user of the db
     * @param databasePassword pwd of the db
     */
    private DBLink(String databaseUrl, String databaseUsername, String databasePassword) {
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.databaseUrl = databaseUrl;
    }

    /**
     * opens connection to the database
     * @throws DBLUnderlyingException ¯\_(ツ)_/¯
     */
    public void connect() throws DBLUnderlyingException {
        try {
            this.connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        } catch (SQLException e) {
            throw new DBLUnderlyingException(e);
        }
    }

    /**
     * checks if the connection to the database is opened
     * @return true if open, false if not
     * @throws DBLUnderlyingException ¯\_(ツ)_/¯
     */
    public boolean isOpen() throws DBLUnderlyingException {
        try {
            return !this.connection.isClosed();
        } catch (SQLException e) {
            throw new DBLUnderlyingException(e);
        }
    }

    /**
     * closes the connection to the database
     * should be reopenable
     * @throws DBLUnderlyingException usually when the connection is already closed
     */
    public void close() throws DBLUnderlyingException {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new DBLUnderlyingException(e);
        }
    }

    /**
     * issues a new prepared query
     * @param query string with wildcards ( "?" )
     * @param expectedParameterTypes list of the expected parameters types
     * @return new query with the specified string and parameters
     * @throws PQWrongParameterException if the parameter count doesn't match with the query
     * @throws DBLUnderlyingException underlying connection exception
     */
    public PreparedQuery prepareQuery(String query, ArrayList<PreparedStatementTypes> expectedParameterTypes) throws PQWrongParameterException, DBLUnderlyingException {
        if(!PreparedQuery.doesQueryMatchesExpectedParameters(query, expectedParameterTypes)){
            throw new PQWrongParameterException();
        }
        try {
            return new PreparedQuery(connection.prepareStatement(query), expectedParameterTypes);
        } catch (SQLException e) {
            throw new DBLUnderlyingException(e);
        }
    }

    /**
     * issues a new prepared query (id-oriented, should only contain one wildcard)
     * @param query string with wildcards ( "?" )
     * @return a new prepared query
     * @throws PQWrongParameterException if there is more than one wildcard in the query
     */
    public PreparedQuery prepareQuery(String query) throws PQWrongParameterException, DBLUnderlyingException {
        if(!PreparedQuery.doesQueryMatchesExpectedParameters(query)){
            throw new PQWrongParameterException();
        }
        try {
            return new PreparedQuery(connection.prepareStatement(query));
        } catch (SQLException e) {
            throw new DBLUnderlyingException(e);
        }
    }

    /**
     * registers the driver to insure it is correctly used
     * @throws DBLDriverNotFoundException if the driver cannot be found, loaded or registered
     */
    private static void registerDriver() throws DBLDriverNotFoundException {
        try {
            Class.forName(DEFAULT_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DBLDriverNotFoundException(e);
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
}
