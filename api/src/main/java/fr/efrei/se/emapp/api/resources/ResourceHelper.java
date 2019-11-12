package fr.efrei.se.emapp.api.resources;

import fr.efrei.se.emapp.api.model.business.Credential;
import fr.efrei.se.emapp.api.model.business.Employee;
import fr.efrei.se.emapp.common.model.CredentialTranscript;
import fr.efrei.se.emapp.common.model.EmployeeTranscript;


/**
 * helps resources
 * will document it later
 * //todo
 */
public class ResourceHelper {
    public static EmployeeTranscript convertEmployee(Employee e) {
        EmployeeTranscript employeeTranscript = new EmployeeTranscript();
        employeeTranscript.setId(e.getId());
        employeeTranscript.setFirstName(e.getFirstName());
        employeeTranscript.setLastName(e.getLastName());
        employeeTranscript.setMobilePhone(e.getMobilePhone());
        employeeTranscript.setHomePhone(e.getHomePhone());
        employeeTranscript.setWorkPhone(e.getWorkPhone());
        employeeTranscript.setAddress(e.getAddress());
        employeeTranscript.setCity(e.getCity());
        employeeTranscript.setPostalCode(e.getPostalCode());
        employeeTranscript.setEmail(e.getEmail());
        return employeeTranscript;
    }

    public static Employee convertEmployeeTranscript(EmployeeTranscript e) {
        return new Employee(e.getId(), e.getFirstName(), e.getLastName(), e.getHomePhone(),
                e.getMobilePhone(), e.getWorkPhone(), e.getAddress(), e.getPostalCode(), e.getCity(), e.getEmail());
    }

    public static CredentialTranscript convertCredential(Credential c) {
        CredentialTranscript credentialTranscript = new CredentialTranscript();
        credentialTranscript.setId(0);
        credentialTranscript.setLogin(c.getLogin());
        credentialTranscript.setPassword(c.getPwd());
        credentialTranscript.setAdmin(c.isAdmin());
        return credentialTranscript;
    }

    public static Credential convertCredentialTranscript(CredentialTranscript c) {
        Credential credential = new Credential();
        credential.setId(c.getId());
        credential.setLogin(c.getLogin());
        credential.setPwd(c.getPassword());
        credential.setAdmin(c.isAdmin());
        return credential;
    }
}
