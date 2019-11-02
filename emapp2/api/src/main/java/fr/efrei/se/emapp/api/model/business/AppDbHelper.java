package fr.efrei.se.emapp.api.model.business;

import fr.efrei.se.emapp.api.model.exception.EmptyResultException;
import fr.efrei.se.emapp.api.model.core.DBLink;
import fr.efrei.se.emapp.api.model.core.PreparedQuery;
import fr.efrei.se.emapp.api.model.core.PreparedStatementTypes;
import fr.efrei.se.emapp.api.model.core.exception.DatabaseCommunicationException;

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
     * @throws DatabaseCommunicationException underlying mechanisms failing
     * @throws EmptyResultException when no credentials where found for the specified parameters
     */
    public Credential checkCredentials(String firstName, String lastName) throws DatabaseCommunicationException, EmptyResultException {
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
