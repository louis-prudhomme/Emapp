package se.m1.emapp.model.core;

import se.m1.emapp.model.core.exception.DriverNotFoundException;
import se.m1.emapp.model.core.exception.WrongPreparedQueryParemeterCountException;

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
     * @throws DriverNotFoundException if the driver is not found
     */
    public static DBLink getNewInstance(String databaseUrl, String databaseUsername, String databasePassword) throws DriverNotFoundException {
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
     * @throws SQLException if an unexpected error occurs
     */
    public void connect() throws SQLException {
        this.connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
    }

    /**
     * checks if the connection to the database is opened
     * @return true if open
     * @throws SQLException ¯\_(ツ)_/¯
     */
    public boolean isOpen() throws SQLException {
        return !this.connection.isClosed();
    }

    /**
     * closes the connection to the database
     * should be reopenable
     * @throws SQLException usually when the connection is already closed
     */
    public void close() throws SQLException {
        this.connection.close();
    }

    /**
     * issues a new prepared query
     * @param query string with wildcards ( "?" )
     * @param expectedParameterTypes list of the expected parameters types
     * @return
     * @throws SQLException
     * @throws WrongPreparedQueryParemeterCountException if the parameter count doesn't match with the query
     */
    public PreparedQuery prepareQuery(String query, ArrayList<PreparedStatementTypes> expectedParameterTypes) throws SQLException, WrongPreparedQueryParemeterCountException {
        if(!PreparedQuery.doesQueryMatchesExpectedParameters(query, expectedParameterTypes)){
            throw new WrongPreparedQueryParemeterCountException();
        }
        return new PreparedQuery(connection.prepareStatement(query), expectedParameterTypes);
    }

    /**
     * issues a new prepared query (id-oriented, should only contain one wildcard)
     * @param query string with wildcards ( "?" )
     * @return a new prepared query
     * @throws SQLException
     * @throws WrongPreparedQueryParemeterCountException if there is more than one wildcard in the query
     */
    public PreparedQuery prepareQuery(String query) throws SQLException, WrongPreparedQueryParemeterCountException {
        if(!PreparedQuery.doesQueryMatchesExpectedParameters(query)){
            throw new WrongPreparedQueryParemeterCountException();
        }
        return new PreparedQuery(connection.prepareStatement(query));
    }

    /**
     * registers the driver to insure it is correctly used
     * @throws DriverNotFoundException if an error occurs
     */
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
}
