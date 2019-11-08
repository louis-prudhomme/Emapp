package fr.efrei.se.emapp.api.resources;

import fr.efrei.se.emapp.api.model.business.Credential;
import fr.efrei.se.emapp.api.model.business.Employee;
import fr.efrei.se.emapp.api.model.core.DBLink;
import fr.efrei.se.emapp.api.model.core.PreparedQuery;
import fr.efrei.se.emapp.api.model.core.PreparedStatementTypes;
import fr.efrei.se.emapp.api.model.core.exception.DBComException;
import fr.efrei.se.emapp.api.model.exception.EmptyParameterException;
import fr.efrei.se.emapp.api.model.exception.EmptyResultException;
import fr.efrei.se.emapp.common.model.CredentialTranscript;
import fr.efrei.se.emapp.common.model.EmployeeTranscript;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ResourceHelper {
    public static EmployeeTranscript convertEmployee(Employee e) {
        EmployeeTranscript employeeTranscript = new EmployeeTranscript();
        employeeTranscript.setId(e.getId());
        employeeTranscript.setFirstName(e.getFirstName());
        employeeTranscript.setLastName(e.getLastName());
        return employeeTranscript;
    }

    public static CredentialTranscript convertCredential(Credential c) {
        CredentialTranscript credentialTranscript = new CredentialTranscript();
        credentialTranscript.setId(c.getId());
        credentialTranscript.setLogin(c.getLogin());
        credentialTranscript.setPassword(c.getPwd());
        return credentialTranscript;
    }

    public static Credential checkCredentials(String firstName, String lastName) throws DBComException, EmptyResultException, EmptyParameterException {
        if(firstName.equals("") || lastName.equals("")) {
            throw new EmptyParameterException();
        }
        String query = "SELECT id FROM CREDENTIAL WHERE LOGIN = ? AND PWD = ?";

        ArrayList<PreparedStatementTypes> parametersTypes = new ArrayList<>(Arrays.asList(PreparedStatementTypes.STRING, PreparedStatementTypes.STRING));
        ArrayList<String> parameters = new ArrayList<>(Arrays.asList(firstName, lastName));

        PreparedQuery preparedQuery = getLink().prepareQuery(query, parametersTypes);
        ResultSet resultSet = preparedQuery.executeQuery(parameters);

        try {
            resultSet.next();
            Credential credential = new Credential(getLink(), resultSet.getInt("id"));
            credential.read();
            return credential;
        } catch (SQLException e) {
            throw new EmptyResultException(e);
        }
    }

    static DBLink getLink() throws DBComException {
        DBLink dbLink = DBLink.getNewInstance("jdbc:mysql://localhost:3306/JEEPRJ", "test", "password");
        dbLink.connect();
        return dbLink;
    }
}
