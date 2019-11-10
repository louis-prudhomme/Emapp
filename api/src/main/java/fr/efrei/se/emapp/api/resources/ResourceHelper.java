package fr.efrei.se.emapp.api.resources;

import fr.efrei.se.emapp.api.model.business.Credential;
import fr.efrei.se.emapp.api.model.business.Employee;
import fr.efrei.se.emapp.common.model.CredentialTranscript;
import fr.efrei.se.emapp.common.model.EmployeeTranscript;


/**
 * helps resources
 * most of this class should disappear with the oncoming jpa update
 * will document it later
 * //todo
 */
public class ResourceHelper {
    public static EmployeeTranscript convertEmployee(Employee e) {
        EmployeeTranscript employeeTranscript = new EmployeeTranscript();
        employeeTranscript.setId(e.getId());
        employeeTranscript.setFirstName(e.getFirstName());
        employeeTranscript.setLastName(e.getLastName());
        return employeeTranscript;
    }

    public static Employee convertEmployeeTranscript(EmployeeTranscript e) {
        return new Employee(e.getId(), e.getFirstName(), e.getLastName(), "", "", "", "", "", "", "");
    }

    public static CredentialTranscript convertCredential(Credential c) {
        CredentialTranscript credentialTranscript = new CredentialTranscript();
        credentialTranscript.setId(0);
        credentialTranscript.setLogin(c.getLogin());
        credentialTranscript.setPassword(c.getPwd());
        return credentialTranscript;
    }
}
