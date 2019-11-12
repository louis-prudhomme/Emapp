package se.m1.emapp.model.core;

import se.m1.emapp.model.core.exception.dbObject.*;
import se.m1.emapp.model.core.exception.dbLink.DBLException;
import se.m1.emapp.model.core.exception.preparedQuery.PQException;
import se.m1.emapp.utils.Tuple;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents database object.
 * This abstract class only states base methods to mark classes as database objects
 * It must be inherited to provide its full effects
 *
 * Upon inheritance, the child class MUST have the name of the represented object in the database
 * Each field MUST have the same name, too
 * Each database field MUST have public getter and setter whose naming convention MUST be « get<FieldName> »
 */
public abstract class DBObject {

    /**
     * Identifier of the object in the databse ;
     * It is equal to zero when the object has not yet been commited to db
     * Integer because the selectAll method crashes otherwise
     */
    private Integer id;

    /**
     * {@link DBLink} instance representing the database connection
     */
    private DBLink dbLink;

    /**
     * default constructor, will serve for new object instances, not yet commited to database
     * @param dbLink {@link DBLink} instance representing the database connection
     */
    public DBObject(DBLink dbLink) {
        this.dbLink = dbLink;
    }

    /**
     * constructor for objects already stored in database (thus, with id)
     * @param dbLink {@link DBLink} instance representing the database connection
     * @param id of the object in the db
     */
    public DBObject(DBLink dbLink, Integer id) {
        this.id = id;
        this.dbLink = dbLink;
    }

    /**
     * Creates a record corresponding the object instance in the database
     * @return returns the number of database lines affected by the instruction
     * @throws PQException {@link PreparedQuery}-related exception
     * @throws DBOException {@link DBObject}-related exception
     * @throws DBLException {@link DBLink}-related exception
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
     * Reads the corresponding object (via its ID) in the database
     * @throws PQException {@link PreparedQuery}-related exception
     * @throws DBOException {@link DBObject}-related exception
     * @throws DBLException {@link DBLink}-related exception
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
     * Parses the answer contained in a {@link ResultSet}
     * @param resultSet result contained in the {@link ResultSet}
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
     * Deletes the object record in the database
     * @throws PQException {@link PreparedQuery}-related exception
     * @throws DBLException {@link DBLink}-related exception
     */
    public int delete() throws PQException, DBLException {
        String query = "DELETE FROM " +
                this.getClass().getSimpleName() +
                " WHERE ID = ?";
        PreparedQuery preparedQuery = dbLink.prepareQuery(query);
        return preparedQuery.executeUpdate(id);
    }

    /**
     * Gets all the classe's {@link Field} and return their names, types and values for the ongoing instance
     * for each reflection-obtained {@link Field} of the class,
     * it will try to obtain and invoke the corresponding getter
     * @return {@link HashMap} of the classe's fields
     * @throws DBOUnreachableGetter if either a getter cannot be found, accessed or invoked
     */
    private HashMap<String, Tuple<Class, Object>> getFieldsAndValues() throws DBOUnreachableGetter {
        HashMap<String, Tuple<Class, Object>> fieldsAndValues = new HashMap<>();

        fieldsAndValues.put("id", new Tuple<>(Integer.class, id));

        for (Field f : this.getClass().getDeclaredFields()) {
            //capitalization is necessary to comply with naming convention
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
     * Converts {@link java.lang.reflect.Type} to {@link PreparedStatementTypes} to allow generic query generation
     * @param type {@link Field} type
     * @return {@link PreparedStatementTypes} matching the initial {@link java.lang.reflect.Type}
     * @throws DBOCannotDetectFieldType if there is not matching {@link PreparedStatementTypes}
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

    /**
     * Getter for {@link DBObject} id
     * @return the {@link DBObject} id
     */
    public int getId() {
        return id;
    }

    /**
     * Capitalizes the first letter of a {@link String}
     * « exempleString » → « ExempleString »
     * @param s {@link String} to capitalize
     * @return capitalized {@link String}
     */
    private static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * Selects all the records for a certain {@link DBObject} child
     * 1 Selects everything in the table corresponding to the {@link Class} given in parameter,
     * 2 then creates a corresponding @link Class}
     * 3 and asks it to parse all the remaining informations
     * @param dbLink {@link DBLink} instance representing the database connection
     * @param target {@link DBObject} child target
     * @param <T> {@link DBObject} child, to insure type consistency
     * @return all the records
     * @throws PQException {@link PreparedQuery}-related exception
     * @throws DBOException {@link DBObject}-related exception
     * @throws DBLException {@link DBLink}-related exception
     */
    public static <T extends DBObject> ArrayList<T> selectAll(DBLink dbLink, Class<T> target) throws PQException, DBLException, DBOException {
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