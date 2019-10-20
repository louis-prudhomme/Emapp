package se.m1.emapp;

import se.m1.emapp.model.business.AppDbHelper;
import se.m1.emapp.model.business.Employee;
import se.m1.emapp.model.core.DBLink;

import java.util.ArrayList;


public class Test {
    public static void main(String[] args) {
        try {
            DBLink dbLink = DBLink.getNewInstance("jdbc:mysql://localhost:3306/test","test","password");

            dbLink.connect();

            Employee employee = new AppDbHelper(dbLink).checkCredentials("jar jar", "binks");
            System.out.println(employee.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
