# Emapp

Emapp is the J2E project we had to develop in our first master’s year in Software Engineering at Efrei Paris.

It consists of a simple application that aims to manage an employee list.

# Versions

Each of the three versions resides on a separate branch. They are conveniently named « v1 », « v2 » and « v3 ».

## 1

- [x] JSP, Java Beans, Servlet and JDBC
- [x] Database .properties file
- [x] SQL scripts in /WEB-INF
- [x] JSPs in /WEB-INF
- [x] Java DB

To pull this particular version, use `git checkout v1`

## 2

- [x] Maven project
- [x] JSP (EL and JSTL only)
- [x] Java Beans
- [x] Optimized Servlet
- [x] JPA Persistence
- [x] MySQL DB

To pull this particular version, use `git checkout v2`

## 3
- [x] RESTful Services
- [ ] Remote DBMS solution
- [ ] Sonarqube
- [ ] JUnit

To pull this particular version, use `git checkout v3`

## Bonus

- [ ] Cloud-deployed

# Technical environment

We used 
- IntelliJ and NetBeans as IDEs
- macOS and Windows as OSes

This imposed us to start the project with Maven

We also used MySQL (via XAMPP and MAMP), on port 8889

# Database evolution according to versions : 
- Database settings :
```
Name:JEEPRJ
User:jee
Password:jee
```

- V1 & V2 : 

The EMPLOYEE table :
```
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
Modification of the DB : adding of a field named "ADMINSTATUS" in the CREDENTIAL table : 
```
ADMINSTATUS tinyint
```

# Technical choices

## DBLink (version 1)

Knowing the benefits of persistence, we thought it would be a good idea to replicate these mecanisms, which we did in the first version using PreparedQueries and Reflection. 

The result works quite well even though it is quite massive.

## Dark theme (version 2)

We worked on this project very hard and usually quite late at night ; we did not like scraching our eyes over blank pages so we made this dark theme.

## Multi-module (version 3)

We decided it would be better to separate the different parts of the application ; with this goal in mind, we broke it into three different Maven modules.

- api represents the REST API part
- web represents the webapp part
- common is what is shared between the two others

Common does not produce any WAR package.

## Multi-branch organization

Throghout the project we made heavy use of git branches.
