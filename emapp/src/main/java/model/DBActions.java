/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Employee;
import static utils.Constants.*;
/**
 *
 * @author melaniemarques
 */
public class DBActions {
    Connection conn;
    Statement stmt;
    ResultSet rs;
    ArrayList<Employee> listEmployees;
    
    
    public DBActions(){
        try {
            conn = DriverManager.getConnection(URL, USER, PWD);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public Statement getStatement() {
        try {
            stmt = conn.createStatement();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return stmt;

    }

    public ResultSet getResultSet(String query) {
        stmt = getStatement();
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return rs;

    }
    
    public ResultSet getResultSet2(String query) {
        stmt = getStatement();
        try {
           stmt.executeUpdate(query);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return rs;

    }

    public ArrayList<Employee> getEmployee() {
        listEmployees = new ArrayList<>();
        rs = getResultSet(QUERY_SEL_EMPLOYEES);
        try {
            while (rs.next()) {
                Employee userBean = new Employee();
                userBean.setName(rs.getString("LOGIN"));
                userBean.setFirstname(rs.getString("PWD"));

                listEmployees.add(userBean);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listEmployees;
    }
    
    public boolean checkCredentials(Employee userInput) {
        boolean test = false;
        listEmployees = getEmployees();

        for (Employee userBase : listEmployees) {
            if (userBase.getName().equals(userInput.getName())
                    && userBase.getFirstname().equals(userInput.getFirstname())) {
                test = true;
            }
        }
        return test;
    }
    
    public ArrayList<Employee> getEmployees() {
        listEmployees = new ArrayList<>();
        rs = getResultSet(QUERY_SEL_EMPLOYEES);
        try {
            while (rs.next()) {
                Employee emplBean = new Employee();             
                emplBean.setFirstname(rs.getString("FIRSTNAME"));
                emplBean.setName(rs.getString("NAME"));
                emplBean.setHomePhone(rs.getString("TELHOME"));
                emplBean.setMobilePhone(rs.getString("TELMOB"));
                emplBean.setWorkPhone(rs.getString("TELPRO"));
                emplBean.setAdress(rs.getString("ADRESS"));
                emplBean.setPostalCode(rs.getString("POSTALCODE"));
                emplBean.setCity(rs.getString("CITY"));
                emplBean.setEmail(rs.getString("EMAIL"));
                emplBean.setIsAdmin(rs.getBoolean("ADMIN"));

                listEmployees.add(emplBean);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listEmployees;
    }
    
    public void createEmployee(Employee e){
        String name = e.getName();
        String firstname = e.getFirstname();
        String telhome = e.getHomePhone();
        String telmob = e.getMobilePhone();
        String telpro = e.getWorkPhone();
        String adress = e.getAdress();
        String postalCode = e.getPostalCode();
        String city = e.getCity();
        String email = e.getEmail();
        String query = "INSERT INTO EMPLOYEES(NAME, FIRSTNAME, TELHOME, TELMOB, TELPRO, ADRESS, POSTALCODE, CITY, EMAIL) VALUES ('"+name+"','"+firstname+"','"+telhome+"','"+telmob+"','"+telpro+"','"+adress+"','"+postalCode+"','"+city+"','"+email+"')"; 
        getResultSet2(query);
    }
    
    
}
