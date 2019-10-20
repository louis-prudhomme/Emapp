package se.m1.emapp.model.business;

import se.m1.emapp.model.core.DBLink;
import se.m1.emapp.model.core.DBObject;

public class Employee extends DBObject {
    private String name;

    public Employee(DBLink dbLink, String name) {
        super(dbLink);
        this.name = name;
    }

    public Employee(DBLink dbLink, Integer id) {
        super(dbLink, id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
