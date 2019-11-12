package se.m1.emapp.model.core;

import se.m1.emapp.model.core.exception.dbLink.DBLDriverNotFoundException;
import se.m1.emapp.model.core.exception.dbLink.DBLUnderlyingException;
import se.m1.emapp.model.core.exception.preparedQuery.PQWrongParameterException;

import java.sql.*;
import java.util.ArrayList;

/**
 * Represents a connection to the database
 * Essentially a decorator for the {@link Connection} class
 */
public class DBLink {
    /**
     * Default driver
     */
    private static final String DEFAULT_DRIVER = "com.mysql.jdbc.Driver";

    /**
     * {@link Connection} parameters ; the DB’s URL, User and Password
     */
    private String databaseUrl, databaseUsername, databasePassword;

    /**
     * {@link Connection} instance which effectively connects
     */
    private Connection connection;

    /**
     * Creates a new {@link DBLink} instance with the given parameters
     * It also registers the database driver
     * @param databaseUrl url of the db
     * @param databaseUsername user of the db
     * @param databasePassword pwd of the db
     * @return new {@link DBLink} instance ; it must be opened manually
     * @throws DBLDriverNotFoundException if the driver is not found
     */
    public static DBLink getNewInstance(String databaseUrl, String databaseUsername, String databasePassword) throws DBLDriverNotFoundException {
        registerDriver();
        return new DBLink(databaseUrl, databaseUsername, databasePassword);
    }

    /**
     * Default constructor
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
     * Opens the connection to the database
     * @throws DBLUnderlyingException when everything fails
     */
    public void connect() throws DBLUnderlyingException {
        try {
            this.connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        } catch (SQLException e) {
            throw new DBLUnderlyingException(e);
        }
    }

    /**
     * Checks if the {@link Connection} to the database is opened
     * @return true if open, false if not
     * @throws DBLUnderlyingException something fails
     */
    public boolean isOpen() throws DBLUnderlyingException {
        try {
            return !this.connection.isClosed();
        } catch (SQLException e) {
            throw new DBLUnderlyingException(e);
        }
    }

    /**
     * Closes the connection to the database
     * Sould be reopenable
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
     * Issues a new {@link PreparedQuery} representing an SQL order
     * @param query {@link String}-formed SQL order with wildcards ( "?" ) in the place of parameters
     * @param expectedParameterTypes {@link ArrayList} of the expected {@link PreparedStatementTypes}
     * @return new query with the specified string and parameters
     * @throws PQWrongParameterException if the parameter count doesn't match with the {@link PreparedQuery}
     * @throws DBLUnderlyingException underlying {@link DBLink} exception
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
     * Issues a new {@link PreparedQuery} (id-oriented, should only contain one wildcard)
     * @param query {@link String}-contained SQL statement with ONE (or zero) wildcard ( "?" )
     * @return a new {@link PreparedQuery}
     * @throws PQWrongParameterException if there is more than one wildcard in the {@link PreparedQuery}
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
     * Registers the database driver to insure it is correctly used
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
