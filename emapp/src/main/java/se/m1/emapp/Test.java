package se.m1.emapp;

import se.m1.emapp.model.business.Employee;
import se.m1.emapp.model.core.DBLink;

import java.util.ArrayList;


public class Test {
    public static void main(String[] args) {
        try {
            DBLink dbLink = DBLink.getNewInstance("jdbc:mysql://localhost:3306/test","test","password");

            dbLink.connect();

            ArrayList<Employee> employees = Employee.selectAll(dbLink, Employee.class);

            for(Employee e: employees) {
                System.out.println(e.getName());
            }

            employees.get(1).setName("marilyn manson");
            employees.get(1).update();

            Employee e1 = new Employee(dbLink, employees.get(1).getId());
            e1.read();
            System.out.println(e1.getName());

            Employee e2 = new Employee(dbLink, "jar jar binks");
            e2.create();

            employees = Employee.selectAll(dbLink, Employee.class);

            for(Employee e: employees) {
                System.out.println(e.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
