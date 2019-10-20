package se.m1.emapp.model;

import se.m1.emapp.model.exception.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//TODO
/*
check credentials
employees crud
    create
    read
    update
    delete
 */

public class PreparedQuery {
    private PreparedStatement preparedStatement;

    private ArrayList<PreparedStatementTypes> expectedParameterTypes;

    PreparedQuery(PreparedStatement preparedStatement, ArrayList<PreparedStatementTypes> expectedParameterTypes) {
        this.preparedStatement = preparedStatement;
        this.expectedParameterTypes = expectedParameterTypes;
    }

    public ResultSet executeQuery(ArrayList<String> parameters) throws PreparedQueryException, SQLException {
        preparedStatement.clearParameters();
        if(parameters.size() > expectedParameterTypes.size()) {
            throw new TooMuchPreparedQueryParametersException();
        } else if (parameters.size() < expectedParameterTypes.size()) {
            throw new TooFewPreparedQueryParametersException();
        } else {
            try {
                treatParameters(parameters);
            } catch (NumberFormatException e) {
                throw new WrongPreparedQueryParameterException();
            }
            return preparedStatement.executeQuery();
        }
    }

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
}
