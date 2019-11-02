package fr.efrei.se.jee.model.core;

import fr.efrei.se.jee.model.core.exception.dbObject.*;
import fr.efrei.se.jee.utils.Tuple;
import fr.efrei.se.jee.model.core.exception.dbObject.*;
import fr.efrei.se.jee.model.core.exception.dbLink.DBLException;
import fr.efrei.se.jee.model.core.exception.dbLink.DBLUnderlyingException;
import fr.efrei.se.jee.model.core.exception.preparedQuery.PQException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class DBObject {

    /**
     * identifier of the object in the databse ;
     * is equal to zero when the object has not yet been commited to db
     * integer because the selectAll method crashes otherwise
     */
    private Integer id;

    /**
     * connection to the database
     */
    private DBLink dbLink;

    /**
     * default constructor, will serve for new object instances, not yet commited to database
     * @param dbLink connection to the database
     */
    public DBObject(DBLink dbLink) {
        this.dbLink = dbLink;
    }

    /**
     * constructor for objects already stored in database (thus, with id)
     * @param dbLink connection to the database
     * @param id of the object in the db
     */
    public DBObject(DBLink dbLink, Integer id) {
        this.id = id;
        this.dbLink = dbLink;
    }

    /**
     * creates a record for the object in the database
     * @throws DBOException
     * @throws PQException
     */
    public int create() throws PQException, DBOException, DBLException {
        HashMap<String, Tuple<Class, Object>> fieldsAndValues = getFieldsAndValues();

        ArrayList<String> fieldValues = new ArrayList<>();
        ArrayList<PreparedStatementTypes> fieldTypes = new ArrayList<>();

        StringBuilder query = new StringBuilder();
        StringBuilder values = new StringBuilder();

        query.append("INSERT INTO ");
        query.append(this.getClass().getSimpleName());
        query.append(" (");

        for (String field : fieldsAndValues.keySet()) {
            if(!field.equals("id")) {
                query.append(field);
                query.append(", ");
                values.append("?, ");

                fieldValues.add(fieldsAndValues.get(field).b.toString());
                fieldTypes.add(parseJavaType(fieldsAndValues.get(field).a));
            }
        }

        query.delete(query.length() - 2, query.length());
        values.delete(values.length() - 2, values.length());

        query.append(") VALUES (");
        query.append(values);
        query.append(")");

        PreparedQuery preparedQuery = dbLink.prepareQuery(query.toString(), fieldTypes);
        return preparedQuery.executeUpdate(fieldValues);
    }

    /**
     * reads the object record in the database
     * @throws SQLException
     * @throws PQException
     */
    public void read() throws PQException, DBOException, DBLException {
        String query = "SELECT * FROM " +
                this.getClass().getSimpleName() +
                " WHERE ID = ?";
        PreparedQuery preparedQuery = dbLink.prepareQuery(query);
        ResultSet resultSet = preparedQuery.executeQuery(id);
        try {
            resultSet.next();
            parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DBOUnexploitableResultSetException(e);
        }
    }

    /**
     * parses the results of a query into the field of the object
     * @param resultSet of the query
     * @throws DBOUnreachableSetter when the field corresponding setter is unreachable
     * @throws DBOCannotParseFieldException when the field cannot be parsed
     * @throws DBOCannotDetectFieldType when the field is not recognized
     * @throws DBOUnreachableGetter when the field corresponding getter is unreachable
     */
    void parseResultSet(ResultSet resultSet) throws DBOUnreachableMethod, DBOCannotParseFieldException, DBOCannotDetectFieldType {
        HashMap<String, Tuple<Class, Object>> fieldsAndValues = getFieldsAndValues();
        for (String field : fieldsAndValues.keySet()) {
            if(!field.equals("id")) {
                String setterName = "set" + capitalize(field);
                try {
                    Method setter = this.getClass().getDeclaredMethod(setterName, fieldsAndValues.get(field).a);
                    parseResult(resultSet, setter, field, fieldsAndValues.get(field).a);
                } catch(NoSuchMethodException e) {
                    throw new DBOUnreachableSetter(e);
                }
            }
        }
    }

    /**
     * parses one line of a result set
     * @param resultSet source
     * @param setter method to set value
     * @param field name of the targeted field
     * @param type type of the field
     * @throws DBOCannotDetectFieldType when the type of the field cannot be detected
     * @throws DBOCannotParseFieldException when the field cannot be parsed
     * @throws DBOUnreachableSetter when the parser cannot be reached (or invoked)
     */
    private void parseResult(ResultSet resultSet, Method setter, String field, Class type) throws DBOCannotDetectFieldType, DBOCannotParseFieldException, DBOUnreachableSetter {
        try {
            switch (parseJavaType(type)) {
                case INT:
                    setter.invoke(this, resultSet.getInt(field));
                    break;
                case FLOAT:
                    setter.invoke(this, resultSet.getFloat(field));
                    break;
                case BOOLEAN:
                    setter.invoke(this, resultSet.getBoolean(field));
                    break;
                case STRING:
                    setter.invoke(this, resultSet.getString(field));
                    break;
                default:
                    throw new DBOCannotDetectFieldType();
            }
        } catch (SQLException e) {
            throw new DBOCannotParseFieldException(e);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DBOUnreachableSetter(e);
        }
    }

    /**
     * updates the object record in the database
     * @throws DBOException
     * @throws PQException
     */
    public int update() throws DBOException, PQException, DBLException {
        HashMap<String, Tuple<Class, Object>> fieldsAndValues = getFieldsAndValues();
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ");
        query.append(this.getClass().getSimpleName());
        query.append(" SET");

        for (String field : fieldsAndValues.keySet()) {
            if(!field.equals("id")) {
                query.append(" ");
                query.append(field);
                query.append(" = ");
                if (fieldsAndValues.get(field).a.equals(String.class)) {
                    query.append("'");
                    query.append(fieldsAndValues.get(field).b);
                    query.append("'");
                } else {
                    query.append(fieldsAndValues.get(field).b);
                }
                query.append(",");
            }
        }
        query.delete(query.length() - 1, query.length());
        query.append(" WHERE ID = ?");

        PreparedQuery preparedQuery = dbLink.prepareQuery(query.toString());
        return preparedQuery.executeUpdate(id);
    }

    /**
     * deletes the object record in the database
     * @throws PQException prepared query exception, usually when parameters are incorrect
     * @throws DBLUnderlyingException problem with the database
     */
    public int delete() throws PQException, DBLException {
        String query = "DELETE FROM " +
                this.getClass().getSimpleName() +
                " WHERE ID = ?";
        PreparedQuery preparedQuery = dbLink.prepareQuery(query);
        return preparedQuery.executeUpdate(id);
    }

    /**
     * gets all the classe's fields and return their names, types and values for the ongoing instance
     * for each reflection-obtained field of the class,
     * it will try to obtain and invoke the corresponding getter
     * @return hashmap of the classe's fields
     * @throws DBOUnreachableGetter if either a getter cannot be found, accessed or invoked
     */
    private HashMap<String, Tuple<Class, Object>> getFieldsAndValues() throws DBOUnreachableGetter {
        HashMap<String, Tuple<Class, Object>> fieldsAndValues = new HashMap<>();

        fieldsAndValues.put("id", new Tuple<>(Integer.class, id));

        for (Field f : this.getClass().getDeclaredFields()) {
            String getterName = "get" + capitalize(f.getName());
            try {
                Method getter = this.getClass().getDeclaredMethod(getterName);
                fieldsAndValues.put(f.getName(), new Tuple<>(f.getType(), getter.invoke(this)));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new DBOUnreachableGetter(e);
            }
        }

        return fieldsAndValues;
    }

    /**
     * converts java types to prepared statement types to allow generic query generation
     * @param type of the field
     * @return prepared statement type matching the initial field
     * @throws DBOCannotDetectFieldType if there is not matching prepared statement type
     */
    private PreparedStatementTypes parseJavaType(Class type) throws DBOCannotDetectFieldType {
        switch (type.getSimpleName()) {
            case "Integer":
                return PreparedStatementTypes.INT;
            case "Float":
                return PreparedStatementTypes.FLOAT;
            case "String":
                return PreparedStatementTypes.STRING;
            case "boolean":
                return PreparedStatementTypes.BOOLEAN;
            default:
                throw new DBOCannotDetectFieldType();
        }
    }

    public int getId() {
        return id;
    }

    public DBLink getDbLink() {
        return dbLink;
    }

    /**
     * capitalizes the first letter of a string
     * @param s string to capitalize
     * @return capitalized string
     */
    private static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * selects all the records for a certain type of dbobject
     * 1 selects everything in the table corresponding to the class given in parameter,
     * 2 then creates a corresponding class
     * 3 and askes it to parse all the remaining informations
     * @param dbLink database connection
     * @param target dbobject
     * @param <T> dbobject
     * @return all the records
     * @throws PQException prepared query exception, usually when a parameter is incorrect
     * @throws DBLUnderlyingException problem with database connection, usually when its closed
     * @throws DBOException problem with the data object, usually a missing field / getter / setter / constructor or database column
     */
    public static <T extends DBObject> ArrayList<T> selectAll(DBLink dbLink, Class<T> target) throws PQException, DBLUnderlyingException, DBOException {
        ArrayList<T> objects = new ArrayList<>();

        String query = "SELECT * FROM " + target.getSimpleName();
        PreparedQuery preparedQuery = dbLink.prepareQuery(query);
        ResultSet resultSet = preparedQuery.executeQuery();

        try {
            Constructor<T> constructor = target.getConstructor(DBLink.class, Integer.class);
            while (resultSet.next()) {
                objects.add(constructor.newInstance(dbLink, resultSet.getInt("id")));
                objects.get(objects.size() - 1).parseResultSet(resultSet);
            }
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new DBOUnreachableConstructor(e);
        } catch (SQLException e) {
            throw new DBOUnexploitableResultSetException(e);
        }

        return objects;
    }
}