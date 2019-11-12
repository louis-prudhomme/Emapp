# Emapp

Emapp is the J2E project we had to develop in our first master’s year in Software Engineering at Efrei Paris.

It consists of a simple application that aims to manage an employee list.

# Versions

## 1

- [x] JSP, Java Beans, Servlet and JDBC
- [x] Database .properties file
- [x] SQL scripts in /WEB-INF
- [x] JSPs in /WEB-INF
- [x] Java DB

To pull this particular version, use `git`

## 2

- [x] Maven project
- [x] JSP (EL and JSTL only)
- [x] Java Beans
- [x] Optimized Servlet
- [x] JPA Persistence
- [x] MySQL DB

To pull this particular version, use `git`

## 3
- [x] RESTful Services
- [ ] Remote DBMS solution
- [ ] Sonarqube
- [ ] JUnit

To pull this particular version, use `git`

## Bonus

- [ ] Cloud-deployed

# Technical environment

We used 
- IntelliJ and NetBeans as IDEs
- macOS and Windows as OSes

This imposed us to start the project with Maven

We also used MySQL (via XAMPP and MAMP), on port 8889

# Database evolution according to versions : 
```
- V1 & V2 : 

ID int(32) AUTO_INCREMENT
FIRSTNAME varchar(128)
LASTNAME varchar(128)
HOMEPHONE varchar(128)
MOBILEPHONE varchar(128)
WORKPHONE varchar(128)
ADDRESS varchar(128)
POSTALCODE varchar(128)
CITY varchar(128)
EMAIL varchar(128)
```

- V3 : 

Modification of the DB : adding of a field named "ADMINSTATUS" : 

- ADMINSTATUS tinyint
