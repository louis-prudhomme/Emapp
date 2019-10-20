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
    
    
    public DBActions(String dbUrl, String dbUser, String dbPwd){
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
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
    
    public ResultSet getResultSetUpdate(String query) {
        stmt = getStatement();
        try {
           stmt.executeUpdate(query);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return rs;

    }
    
    public boolean checkCredentials(Employee userInput) {
        boolean test = false;
        listEmployees = getEmployees(QUERY_SEL_EMPLOYEES);

        for (Employee userBase : listEmployees) {
            if (userBase.getName().equals(userInput.getName())
                    && userBase.getFirstname().equals(userInput.getFirstname())) {
                test = true;
            }
        }
        return test;
    }
    
    public ArrayList<Employee> getEmployees(String query) {
        listEmployees = new ArrayList<>();
        rs = getResultSet(query);
        try {
            while (rs.next()) {
                Employee emplBean = new Employee(); 
                emplBean.setId(rs.getInt("ID"));
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
        getResultSetUpdate(query);
    }
    
    public void deleteEmployee(String e){
        String query = "DELETE FROM EMPLOYEES WHERE ID = "+e;
        getResultSetUpdate(query);
    }
    
    public Employee getEmployeeDetails(String e){
        String query = "SELECT * from EMPLOYEES WHERE ID = "+e;
        listEmployees = getEmployees(query);
        return(listEmployees.get(0));    
    }
    
    public void updateEmployee(int id, Employee e){
        String query = "UPDATE EMPLOYEES SET NAME = '"+e.getName()+"' , FIRSTNAME ='"+e.getFirstname() +"', TELHOME ='"+e.getHomePhone()+"', TELMOB ='"+e.getMobilePhone()+"', TELPRO ='"+e.getWorkPhone()+"' , ADRESS ='"+e.getAdress()+"' , POSTALCODE ='"+e.getPostalCode()+"', CITY ='"+e.getCity()+"' , EMAIL ='"+e.getEmail()+"' WHERE ID ="+id;
        getResultSetUpdate(query);
    }
    
    
}
