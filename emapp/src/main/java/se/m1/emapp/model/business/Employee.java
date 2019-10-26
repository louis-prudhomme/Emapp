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
    @NamedQuery(name = "Employee.findById", query = "SELECT e FROM Employee e WHERE e.id = :id"),
    @NamedQuery(name = "Employee.findByFirstname", query = "SELECT e FROM Employee e WHERE e.firstname = :firstname"),
    @NamedQuery(name = "Employee.findByLastname", query = "SELECT e FROM Employee e WHERE e.lastname = :lastname"),
    @NamedQuery(name = "Employee.findByHomephone", query = "SELECT e FROM Employee e WHERE e.homephone = :homephone"),
    @NamedQuery(name = "Employee.findByMobilephone", query = "SELECT e FROM Employee e WHERE e.mobilephone = :mobilephone"),
    @NamedQuery(name = "Employee.findByAddress", query = "SELECT e FROM Employee e WHERE e.address = :address"),
    @NamedQuery(name = "Employee.findByPostalcode", query = "SELECT e FROM Employee e WHERE e.postalcode = :postalcode"),
    @NamedQuery(name = "Employee.findByCity", query = "SELECT e FROM Employee e WHERE e.city = :city"),
    @NamedQuery(name = "Employee.findByEmail", query = "SELECT e FROM Employee e WHERE e.email = :email")})

public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 128)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Size(max = 128)
    @Column(name = "LASTNAME")
    private String lastname;
    @Size(max = 128)
    @Column(name = "HOMEPHONE")
    private String homephone;
    @Size(max = 128)
    @Column(name = "MOBILEPHONE")
    private String mobilephone;
    @Size(max = 128)
    @Column(name = "WORKPHONE")
    private String workphone;
    @Size(max = 128)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 128)
    @Column(name = "POSTALCODE")
    private String postalcode;
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
        this.firstname = Firstname;
        this.lastname = Lastname;
        this.homephone = HomePhone;
        this.mobilephone = MobilePhone;
        this.workphone = WorkPhone;
        this.postalcode = PostalCode;
        
    }
    
    public Employee(String Firstname, String Lastname, String HomePhone, String MobilePhone, String WorkPhone, String address, String PostalCode, String City, String email ){
        this.address = address;
        this.city = City;
        this.email = email;
        this.lastname = Lastname;
        this.firstname = Firstname;
        this.homephone = HomePhone;
        this.mobilephone = MobilePhone;
        this.workphone = WorkPhone;
        this.postalcode = PostalCode;
       
       
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getHomePhone() {
        return homephone;
    }

    public void setHomePhone(String homephone) {
        this.homephone = homephone;
    }

    public String getMobilePhone() {
        return mobilephone;
    }

    public void setMobilePhone(String mobilephone) {
        this.mobilephone = mobilephone;
    }
    
    public String getWorkPhone() {
        return workphone;
    }

    public void setWorkPhone(String workphone) {
        this.workphone = workphone;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalcode;
    }

    public void setPostalCode(String postalcode) {
        this.postalcode = postalcode;
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
