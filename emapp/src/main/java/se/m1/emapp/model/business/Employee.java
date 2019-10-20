package se.m1.emapp.model.business;

import se.m1.emapp.model.core.DBLink;
import se.m1.emapp.model.core.DBObject;

public class Employee extends DBObject {
    private String firstName;
    private String lastName;
    private String homePhone;
    private String mobilePhone;
    private String workPhone;
    private String address;
    private String postalCode;
    private String city;
    private String email;
    private boolean adminStatus;

    public Employee(DBLink dbLink, Integer id) {
        super(dbLink, id);
    }

    public Employee(DBLink dbLink, Integer id, String firstName, String lastName, String homePhone, String mobilePhone, String workPhone, String address, String postalCode, String city, String email, boolean adminStatus) {
        super(dbLink, id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.homePhone = homePhone;
        this.mobilePhone = mobilePhone;
        this.workPhone = workPhone;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.email = email;
        this.adminStatus = adminStatus;
    }

    public Employee(DBLink dbLink, String firstName, String lastName, String homePhone, String mobilePhone, String workPhone, String address, String postalCode, String city, String email, boolean adminStatus) {
        super(dbLink);
        this.firstName = firstName;
        this.lastName = lastName;
        this.homePhone = homePhone;
        this.mobilePhone = mobilePhone;
        this.workPhone = workPhone;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.email = email;
        this.adminStatus = adminStatus;
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

    public boolean getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(boolean adminStatus) {
        this.adminStatus = adminStatus;
    }
}
