package se.m1.emapp.model.business;

import se.m1.emapp.model.exception.EmptyResultException;
import se.m1.emapp.model.core.DBLink;
import se.m1.emapp.model.core.PreparedQuery;
import se.m1.emapp.model.core.PreparedStatementTypes;
import se.m1.emapp.model.core.exception.DatabaseCommunicationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents non-standard database interactions
 * Non-standard meaning not directly related to a {@link se.m1.emapp.model.core.DBObject}
 */
public class AppDbHelper {
    /**
     * {@link DBLink} instance representing the database connection
     */
    private DBLink dbLink;

    /**
     * Default constructor
     * @param dbLink {@link DBLink} instance representing the database connection
     */
    public AppDbHelper(DBLink dbLink) {
        this.dbLink = dbLink;
    }

    /**
     * Returns a matching {@link Employee}
     * @param firstName of the {@link Employee}
     * @param lastName of the {@link Employee}
     * @return matching {@link Employee}
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
