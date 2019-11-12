/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.m1.emapp.model.business;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "EMPLOYEE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
    @NamedQuery(name = "Employee.findById", query = "SELECT e FROM Employee e WHERE e.id = :id")})
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 128)
    @Column(name = "FIRSTNAME")
    private String firstName;
    @Size(max = 128)
    @Column(name = "LASTNAME")
    private String lastName;
    @Size(max = 128)
    @Column(name = "HOMEPHONE")
    private String homePhone;
    @Size(max = 128)
    @Column(name = "MOBILEPHONE")
    private String mobilePhone;
    @Size(max = 128)
    @Column(name = "WORKPHONE")
    private String workPhone;
    @Size(max = 128)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 128)
    @Column(name = "POSTALCODE")
    private String postalCode;
    @Size(max = 128)
    @Column(name = "CITY")
    private String city;
    @Size(max = 128)
    @Column(name = "EMAIL")
    private String email;

    /**
     * default constructor
     */
    public Employee() {
    }
    
    /**
     * constructor
     * @param id 
     */
    public Employee(Integer id) {
        this.id = id;
    }
    
    /**
     * constructor
     * @param id
     * @param Firstname
     * @param Lastname
     * @param HomePhone
     * @param MobilePhone
     * @param WorkPhone
     * @param address
     * @param PostalCode
     * @param City
     * @param email 
     */
    public Employee(Integer id, String Firstname, String Lastname, String HomePhone, String MobilePhone, String WorkPhone, String address, String PostalCode, String City, String email ){
        this.id = id;
        this.address = address;
        this.city = City;
        this.email = email;
        this.firstName = Firstname;
        this.lastName = Lastname;
        this.homePhone = HomePhone;
        this.mobilePhone = MobilePhone;
        this.workPhone = WorkPhone;
        this.postalCode = PostalCode;
        
    }
    
    /**
     * constructor
     * @param Firstname
     * @param Lastname
     * @param HomePhone
     * @param MobilePhone
     * @param WorkPhone
     * @param address
     * @param PostalCode
     * @param City
     * @param email 
     */
    public Employee(String Firstname, String Lastname, String HomePhone, String MobilePhone, String WorkPhone, String address, String PostalCode, String City, String email ){
        this.address = address;
        this.city = City;
        this.email = email;
        this.lastName = Lastname;
        this.firstName = Firstname;
        this.homePhone = HomePhone;
        this.mobilePhone = MobilePhone;
        this.workPhone = WorkPhone;
        this.postalCode = PostalCode;       
    }

    /**
     * id getter
     * @return an integer corresponding to the id of the employee
     */
    public Integer getId() {
        return id;
    }

    /**
     * id setter
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * firstname getter
     * @return a string corresponding to the firstname of the employee
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * firstname getter
     * @param firstName 
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * lastname getter
     * @return a string corresponding to the lastname of the employee
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * lastname setter
     * @param lastName 
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * homephone getter
     * @return a string corresponding to the homephone of the employee
     */
    public String getHomePhone() {
        return homePhone;
    }
    /**
     * homephone setter
     * @param homePhone 
     */
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }
    /**
     * mobilephone getter
     * @return a string corresponding to the mobilephone of the employee
     */
    public String getMobilePhone() {
        return mobilePhone;
    }
    /**
     * mobilephone setter
     * @param mobilePhone 
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    /**
     * workphone getter
     * @return a string corresponding to the workphone of the employee
     */
    public String getWorkPhone() {
        return workPhone;
    }
    /**
     * workphone setter
     * @param workPhone 
     */
    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }
    /**
     * address getter
     * @return a string corresponding to the address of the employee
     */
    public String getAddress() {
        return address;
    }
    /**
     * address setter
     * @param address 
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * postalcode getter
     * @return a string corresponding to the postalcode of the employee
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**
     * postalcode setter
     * @param postalCode 
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * city getter
     * @return a string corresponding to the city of the employee
     */
    public String getCity() {
        return city;
    }
    /**
     * city setter
     * @param city 
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * email getter
     * @return a string corresponding to the email of the employee
     */
    public String getEmail() {
        return email;
    }
    /**
     * email setter
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return an integer
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * 
     * @param object
     * @return 
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "se.m1.emapp.model.business.Employee[ id=" + id + " ]";
    }
    
}