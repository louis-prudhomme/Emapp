package se.m1.emapp.model.business;

import se.m1.emapp.model.exception.EmptyParameterException;
import se.m1.emapp.model.exception.EmptyResultException;
import se.m1.emapp.model.core.DBLink;
import se.m1.emapp.model.core.PreparedQuery;
import se.m1.emapp.model.core.PreparedStatementTypes;
import se.m1.emapp.model.core.exception.DBComException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * represents non-standard database interactions
 */
public class AppDbHelper {
    /**
     * connection to the database
     */
    private DBLink dbLink;

    /**
     * default constructor
     * @param dbLink link to the database
     */
    public AppDbHelper(DBLink dbLink) {
        this.dbLink = dbLink;
    }

    /**
     * returns a matching employee
     * @param firstName duh
     * @param lastName duh
     * @return matching employee
     * @throws DBComException underlying mechanisms failing
     * @throws EmptyResultException when no credentials where found for the specified parameters
     */
    public Credential checkCredentials(String firstName, String lastName) throws DBComException, EmptyResultException, EmptyParameterException {
        if(firstName.equals("") || lastName.equals("")) {
            throw new EmptyParameterException();
        }
        String query = "SELECT id FROM CREDENTIAL WHERE LOGIN = ? AND PWD = ?";

        ArrayList<PreparedStatementTypes> parametersTypes = new ArrayList<>(Arrays.asList(PreparedStatementTypes.STRING, PreparedStatementTypes.STRING));
        ArrayList<String> parameters = new ArrayList<>(Arrays.asList(firstName, lastName));

        PreparedQuery preparedQuery = dbLink.prepareQuery(query, parametersTypes);
        ResultSet resultSet = preparedQuery.executeQuery(parameters);

        try {
            resultSet.next();
            Credential e = new Credential(dbLink, resultSet.getInt("id"));
            e.read();
            return e;
        } catch (SQLException e) {
            throw new EmptyResultException(e);
        }
    }
}
