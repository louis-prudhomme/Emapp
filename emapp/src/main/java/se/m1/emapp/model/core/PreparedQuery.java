package se.m1.emapp.model.core;

import se.m1.emapp.model.core.exception.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

//TODO
/*
check credentials
employees crud
    create
    read
    update
    delete
 */

/**
 * represents a prepared query ;
 * is essentially a java prepared statement throwing exception when type mismatch
 * it cleans itself before each execution and can thus be reused
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
     * @throws PreparedQueryException
     * @throws SQLException
     */
    public ResultSet executeQuery(int id) throws PreparedQueryException, SQLException {
        ArrayList<String> p = new ArrayList<>();
        p.add(String.valueOf(id));
        return this.executeQuery(p);
    }

    /**
     *
     * @param id executes a query with only the id as parameter
     * @return result of the query, usually the number of affected lines (should be one ?)
     * @throws PreparedQueryException
     * @throws SQLException
     */
    public int executeUpdate(int id) throws PreparedQueryException, SQLException {
        ArrayList<String> p = new ArrayList<>();
        p.add(String.valueOf(id));
        return this.executeUpdate(p);
    }

    /**
     *
     * @return result of the query, usually the number of affected lines (should be one ?)
     * @throws PreparedQueryException
     * @throws SQLException
     */
    public int executeUpdate() throws PreparedQueryException, SQLException {
        ArrayList<String> p = new ArrayList<>();
        expectedParameterTypes = new ArrayList<>();
        return this.executeUpdate(p);
    }

    /**
     *
     * @return result of the query, usually the number of affected lines (should be one ?)
     * @throws PreparedQueryException
     * @throws SQLException
     */
    public ResultSet executeQuery() throws PreparedQueryException, SQLException {
        ArrayList<String> p = new ArrayList<>();
        expectedParameterTypes = new ArrayList<>();
        return this.executeQuery(p);
    }

    /**
     * executes a query with the given parameters
     * @param parameters list of the given parameters
     * @return result of the query
     * @throws PreparedQueryException
     * @throws SQLException
     */
    public ResultSet executeQuery(ArrayList<String> parameters) throws PreparedQueryException, SQLException {
        checkQuery(parameters);
        return preparedStatement.executeQuery();
    }

    /**
     * executes a query with the given parameters
     * @param parameters list of the given parameters
     * @return result of the query
     * @throws PreparedQueryException
     * @throws SQLException
     */
    public int executeUpdate(ArrayList<String> parameters) throws PreparedQueryException, SQLException {
        checkQuery(parameters);
        return preparedStatement.executeUpdate();
    }

    /**
     * checks incoming parameters for the query
     * @param parameters list of the desired parameters for the next execution
     * @throws PreparedQueryException
     * @throws SQLException
     */
    private void checkQuery(ArrayList<String> parameters) throws PreparedQueryException, SQLException {
        preparedStatement.clearParameters();
        if(parameters.size() > expectedParameterTypes.size()) {
            throw new TooMuchPreparedQueryParametersException();
        } else if (parameters.size() < expectedParameterTypes.size()) {
            throw new TooFewPreparedQueryParametersException();
        } else {
            try {
                this.treatParameters(parameters);
            } catch (NumberFormatException e) {
                throw new WrongPreparedQueryParameterException();
            }
        }
    }

    /**
     * enforces type check on the query’s parameters
     * @param parameters given parameters
     * @throws SQLException
     */
    private void treatParameters(ArrayList<String> parameters) throws SQLException {
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
    }

    /**
     * checks if a query has the correct amount of parameters
     * @param query target query
     * @param preparedStatementTypes
     * @return
     */
    public static boolean doesQueryMatchesExpectedParameters(String query, ArrayList<PreparedStatementTypes> preparedStatementTypes) {
        return preparedStatementTypes.size() == query.chars().filter(shar -> shar == '?').count();
    }

    /**
     * checks if a query has one parameter (id targeted queries)
     * @param query target query
     * @return
     */
    public static boolean doesQueryMatchesExpectedParameters(String query) {
        return 1 >= query.chars().filter(shar -> shar == '?').count();
    }
}
