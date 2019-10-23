DROP TABLE employee;
CREATE TABLE employee (
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  firstname varchar(128),
  lastname varchar(128),
  homePhone varchar(128),
  mobilePhone varchar(128),
  workPhone varchar(128),
  address varchar(128),
  postalCode varchar(128),
  city varchar(128),
  email varchar(128),
  adminStatus BOOLEAN,
  CONSTRAINT primary_key_employee PRIMARY KEY (id)
);