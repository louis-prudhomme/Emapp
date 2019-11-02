package fr.efrei.se.emapp.api.model.core;

import fr.efrei.se.emapp.api.model.core.exception.preparedQuery.*;
import fr.efrei.se.jee.model.core.exception.preparedQuery.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * represents a prepared query ;
 * is essentially a java prepared statement throwing exception when type mismatch
 * it cleans itself before each execution and can thus be reused
 * todo refactor preparedquery constructor to take a dblink parameter and check parameters type by itself
 */
public class PreparedQuery {
    /**
     * corresponding prepared statement
     */
    private PreparedStatement preparedStatement;

    /**
     * types expected for query evaluation
     */
    private ArrayList<PreparedStatementTypes> expectedParameterTypes;

    /**
     * this constructor serves when the only parameter in the query is an id
     * @param preparedStatement corresponding prepared statement
     */
    PreparedQuery(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
        this.expectedParameterTypes = new ArrayList<>(Arrays.asList(PreparedStatementTypes.INT));
    }

    /**
     * default constructor
     * @param preparedStatement corresponding prepared statement
     * @param expectedParameterTypes types expected for query evaluation
     */
    PreparedQuery(PreparedStatement preparedStatement, ArrayList<PreparedStatementTypes> expectedParameterTypes) {
        this.preparedStatement = preparedStatement;
        this.expectedParameterTypes = expectedParameterTypes;
    }

    /**
     *
     * @param id executes a query with only the id as parameter
     * @return result of the query, usually the number of affected lines (should be one ?)
     * @throws PQWrongParameterException
     * @throws PQUnderlyingException
     * @throws PQWrongParameterCountException
     */
    public ResultSet executeQuery(int id) throws PQUnderlyingException, PQWrongParameterException, PQWrongParameterCountException {
        ArrayList<String> p = new ArrayList<>();
        p.add(String.valueOf(id));
        return this.executeQuery(p);
    }

    /**
     *
     * @param id executes a query with only the id as parameter
     * @return result of the query, usually the number of affected lines (should be one ?)
     * @throws PQWrongParameterException
     * @throws PQUnderlyingException
     * @throws PQWrongParameterCountException
     */
    public int executeUpdate(int id) throws PQUnderlyingException, PQWrongParameterException, PQWrongParameterCountException {
        ArrayList<String> p = new ArrayList<>();
        p.add(String.valueOf(id));
        return this.executeUpdate(p);
    }

    /**
     *
     * @return result of the query, usually the number of affected lines (should be one ?)
     * @throws PQWrongParameterException
     * @throws PQUnderlyingException
     * @throws PQWrongParameterCountException
     */
    public int executeUpdate() throws PQUnderlyingException, PQWrongParameterException, PQWrongParameterCountException {
        ArrayList<String> p = new ArrayList<>();
        expectedParameterTypes = new ArrayList<>();
        return this.executeUpdate(p);
    }

    /**
     *
     * @return result of the query, usually the number of affected lines (should be one ?)
     * @throws PQWrongParameterException
     * @throws PQUnderlyingException
     * @throws PQWrongParameterCountException
     */
    public ResultSet executeQuery() throws PQUnderlyingException, PQWrongParameterException, PQWrongParameterCountException {
        ArrayList<String> p = new ArrayList<>();
        expectedParameterTypes = new ArrayList<>();
        return this.executeQuery(p);
    }

    /**
     * executes a query with the given parameters
     * @param parameters list of the given parameters
     * @return result of the query
     * @throws PQWrongParameterException
     * @throws PQUnderlyingException
     * @throws PQWrongParameterCountException
     */
    public ResultSet executeQuery(ArrayList<String> parameters) throws PQWrongParameterException, PQUnderlyingException, PQWrongParameterCountException {
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
     * executes a query with the given parameters
     * @param parameters list of the given parameters
     * @return result of the query
     * @throws PQWrongParameterException
     * @throws PQUnderlyingException
     * @throws PQWrongParameterCountException
     */
    public int executeUpdate(ArrayList<String> parameters) throws PQWrongParameterCountException, PQWrongParameterException, PQUnderlyingException {
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
     * checks incoming parameters for the query
     * @param parameters list of the desired parameters for the next execution
     * @throws PQTooMuchParameterException when there is too much parameters
     * @throws PQTooFewParameterException when there is too few parameters
     * @throws PQUnderlyingException when there is a problem with the prepared statement (usually, the connection is closed
     * @throws PQWrongParameterException when a wrong-type parameter was given to the query
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
     * enforces type check on the query’s parameters
     * @param parameters given parameters
     * @throws PQWrongParameterException usually, occurs when the parameter’s type is wrong, but can also occur if the connection is closed
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
     * checks if a query has one parameter (id targeted queries)
     * @param query target query
     * @return true if the query as one or zero parameter
     * todo proper system
     */
    public static boolean doesQueryMatchesExpectedParameters(String query) {
        return 1 >= query.chars().filter(shar -> shar == '?').count();
    }
}
