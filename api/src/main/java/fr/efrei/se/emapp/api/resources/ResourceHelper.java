package fr.efrei.se.emapp.api.resources;

import fr.efrei.se.emapp.api.model.business.Credential;
import fr.efrei.se.emapp.api.model.business.Employee;
import fr.efrei.se.emapp.common.model.CredentialTranscript;
import fr.efrei.se.emapp.common.model.EmployeeTranscript;

/**
 * The main purpose of this class is to allow easy conversion between {@link fr.efrei.se.emapp.common.model} package and
 * {@link fr.efrei.se.emapp.api.model} classes (between classical, jpa-managed classes and transcripts)
 */
public class ResourceHelper {
    /**
     * allows conversion from {@link Employee} to {@link EmployeeTranscript}
     * @param e {@link Employee} source for the conversion
     * @return new {@link EmployeeTranscript} similar to the given {@link Employee}
     */
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

    /**
     * allows conversion from {@link EmployeeTranscript} to {@link Employee}
     * @param e {@link EmployeeTranscript} source for the conversion
     * @return new {@link Employee} similar to the given {@link EmployeeTranscript}
     */
    public static Employee convertEmployeeTranscript(EmployeeTranscript e) {
        return new Employee(e.getId(), e.getFirstName(), e.getLastName(), e.getHomePhone(),
                e.getMobilePhone(), e.getWorkPhone(), e.getAddress(), e.getPostalCode(), e.getCity(), e.getEmail());
    }

    /**
     * allows conversion from {@link Credential} to {@link CredentialTranscript}
     * @param c {@link Credential} source for the conversion
     * @return new {@link CredentialTranscript} similar to the given {@link Credential}
     */
    public static CredentialTranscript convertCredential(Credential c) {
        CredentialTranscript credentialTranscript = new CredentialTranscript();
        credentialTranscript.setId(0);
        credentialTranscript.setLogin(c.getLogin());
        credentialTranscript.setPassword(c.getPwd());
        credentialTranscript.setAdmin(c.isAdmin());
        return credentialTranscript;
    }


    /**
     * allows conversion from {@link CredentialTranscript} to {@link Credential}
     * @param c {@link CredentialTranscript} source for the conversion
     * @return new {@link Credential} similar to the given {@link CredentialTranscript}
     */
    public static Credential convertCredentialTranscript(CredentialTranscript c) {
        Credential credential = new Credential();
        credential.setId(c.getId());
        credential.setLogin(c.getLogin());
        credential.setPwd(c.getPassword());
        credential.setAdmin(c.isAdmin());
        return credential;
    }
}
