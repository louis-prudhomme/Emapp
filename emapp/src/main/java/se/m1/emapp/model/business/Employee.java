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
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 128)
    @Column(name = "EMAIL")
    private String email;

    public Employee() {
    }

    public Employee(Integer id) {
        this.id = id;
    }
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.m1.emapp.model.business.Employee[ id=" + id + " ]";
    }
    
}