package fr.efrei.se.emapp.api;

import fr.efrei.se.emapp.api.model.business.Employee;
import fr.efrei.se.emapp.common.model.EmployeeTranscript;

public class EmployeeHelper {
    public static EmployeeTranscript convertEmployee(Employee e) {
        EmployeeTranscript employeeTranscript = new EmployeeTranscript();
        employeeTranscript.setId(e.getId());
        employeeTranscript.setFirstName(e.getFirstName());
        employeeTranscript.setLastName(e.getLastName());
        return employeeTranscript;
    }
}
