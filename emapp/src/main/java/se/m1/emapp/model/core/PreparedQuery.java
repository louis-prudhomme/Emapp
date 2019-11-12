package se.m1.emapp.model.core;

import se.m1.emapp.model.core.exception.preparedQuery.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents an SQL prepared query ;
 * Is essentially a {@link PreparedStatement} decorator with explicit exceptions when types mismatch
 * It cleans itself before each execution and can thus be reused
 */
public class PreparedQuery {
    /**
     * {@link DBLink} instance representing the database connection
     */
    private PreparedStatement preparedStatement;

    /**
     * {@link PreparedStatementTypes} expected for query evaluation
     */
    private ArrayList<PreparedStatementTypes> expectedParameterTypes;

    /**
     * This constructor serves when the only parameter in the query is an id
     * @param preparedStatement underlying {@link PreparedStatement}
     */
    PreparedQuery(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
        this.expectedParameterTypes = new ArrayList<>(Arrays.asList(PreparedStatementTypes.INT));
    }

    /**
     * default constructor
     * @param preparedStatement underlying {@link PreparedStatement}
     * @param expectedParameterTypes {@link PreparedStatementTypes} for query evaluation
     */
    PreparedQuery(PreparedStatement preparedStatement, ArrayList<PreparedStatementTypes> expectedParameterTypes) {
        this.preparedStatement = preparedStatement;
        this.expectedParameterTypes = expectedParameterTypes;
    }

    /**
     * Executes a {@link PreparedStatement} with the given id as only parameter
     * @param id given id
     * @return result of the {@link PreparedQuery}, usually the number of affected lines (should be one ?)
     * @throws PQException {@link PreparedQuery}-related exception
     */
    public ResultSet executeQuery(int id) throws PQException {
        ArrayList<String> p = new ArrayList<>();
        p.add(String.valueOf(id));
        return this.executeQuery(p);
    }

    /**
     * Executes a {@link PreparedStatement} with the given id as only parameter
     * @param id given id
     * @return result of the {@link PreparedQuery}, usually the number of affected lines (should be one ?)
     * @throws PQException {@link PreparedQuery}-related exception
     */
    public int executeUpdate(int id) throws PQException {
        ArrayList<String> p = new ArrayList<>();
        p.add(String.valueOf(id));
        return this.executeUpdate(p);
    }

    /**
     * Executes an update-type SQL statement
     * @return result of the {@link PreparedQuery}, the number of affected lines (should be one ?)
     * @throws PQException {@link PreparedQuery}-related exception
     */
    public int executeUpdate() throws PQException {
        ArrayList<String> p = new ArrayList<>();
        expectedParameterTypes = new ArrayList<>();
        return this.executeUpdate(p);
    }

    /**
     * Executes an query-type SQL statement
     * @return result of the {@link PreparedQuery}, usually the number of affected lines (should be one ?)
     * @throws PQException {@link PreparedQuery}-related exception
     */
    public ResultSet executeQuery() throws PQException {
        ArrayList<String> p = new ArrayList<>();
        expectedParameterTypes = new ArrayList<>();
        return this.executeQuery(p);
    }

    /**
     * executes a query with the given parameters
     * @param parameters {@link ArrayList} of parameters, which should match the expected {@link ArrayList} of {@link PreparedStatementTypes}
     * @return result of the {@link PreparedQuery}
     * @throws PQException {@link PreparedQuery}-related exception
     */
    public ResultSet executeQuery(ArrayList<String> parameters) throws PQException {
        try {
            checkQuery(parameters);
            return preparedStatement.executeQuery();
        } catch (PQWrongParameterCountException e) {
            throw new PQWrongParameterCountException(e);
        } catch (SQLException e) {
            throw new PQUnderlyingException(e);
        }
    }

    /**
     * Executes a query with the given parameters
     * @param parameters {@link ArrayList} of parameters, which should match the expected {@link ArrayList} of {@link PreparedStatementTypes}
     * @return result of the {@link PreparedQuery}
     * @throws PQException {@link PreparedQuery}-related exception
     */
    public int executeUpdate(ArrayList<String> parameters) throws PQException {
        try {
            checkQuery(parameters);
            return preparedStatement.executeUpdate();
        } catch (PQWrongParameterCountException e) {
            throw new PQWrongParameterCountException(e);
        } catch (SQLException e) {
            throw new PQUnderlyingException(e);
        }
    }

    /**
     * Checks incoming parameters for the {@link PreparedQuery}
     * @param parameters {@link ArrayList} of parameters
     * @throws PQTooMuchParameterException when there is too much parameters
     * @throws PQTooFewParameterException when there is too few parameters
     * @throws PQUnderlyingException when there is a problem with the {@link PreparedStatement} (usually, the {@link java.sql.Connection} is closed
     * @throws PQWrongParameterException when a wrong-type parameter was given to the {@link PreparedQuery}
     */
    private void checkQuery(ArrayList<String> parameters) throws PQTooMuchParameterException, PQTooFewParameterException, PQUnderlyingException, PQWrongParameterException {
        if(parameters.size() > expectedParameterTypes.size()) {
            throw new PQTooMuchParameterException();
        } else if (parameters.size() < expectedParameterTypes.size()) {
            throw new PQTooFewParameterException();
        } else {
            try {
                preparedStatement.clearParameters();
                this.treatParameters(parameters);
            } catch (NumberFormatException e) {
                throw new PQWrongParameterException(e);
            } catch (SQLException e) {
                throw new PQUnderlyingException(e);
            }
        }
    }

    /**
     * Enforces type check on the query’s parameters
     * @param parameters {@link ArrayList} of parameters
     * @throws PQWrongParameterException usually, occurs when at least one parameter type is wrong, but can also occur if the connection is closed
     */
    private void treatParameters(ArrayList<String> parameters) throws PQWrongParameterException {
        try {
            for (int i = 0; i < parameters.size(); i++) {
                switch (expectedParameterTypes.get(i)){
                    case INT:
                        preparedStatement.setInt(i + 1, Integer.parseInt(parameters.get(i)));
                        break;
                    case FLOAT:
                        preparedStatement.setFloat(i + 1, Float.parseFloat(parameters.get(i)));
                        break;
                    case BOOLEAN:
                        preparedStatement.setBoolean(i + 1, Boolean.parseBoolean(parameters.get(i)));
                        break;
                    case STRING:
                    default:
                        preparedStatement.setString(i + 1, parameters.get(i));
                }
            }
        } catch (SQLException e) {
            throw new PQWrongParameterException(e);
        }
    }

    /**
     * checks if a query has the correct amount of parameters
     * @param query target query
     * @param preparedStatementTypes list of the parameters
     * @return true if the parameter count matches the occurences of « ? » in the query
     */
    public static boolean doesQueryMatchesExpectedParameters(String query, ArrayList<PreparedStatementTypes> preparedStatementTypes) {
        return preparedStatementTypes.size() == query.chars().filter(shar -> shar == '?').count();
    }

    /**
     * Checks if a {@link String}-contained query has one parameter or less (for id-related queries)
     * @param query target {@link PreparedQuery}
     * @return true if the {@link PreparedQuery} as one or zero parameter
     * todo proper system
     */
    public static boolean doesQueryMatchesExpectedParameters(String query) {
        return 1 >= query.chars().filter(shar -> shar == '?').count();
    }
}
