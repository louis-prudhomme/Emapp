package se.m1.emapp.model;

import se.m1.emapp.model.exception.*;
import utils.Tuple;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class DBObject {

    /**
     * identifier of the object in the databse ;
     * is equal to zero when the object has not yet been commited to db
     */
    private int id;

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
    public DBObject(DBLink dbLink, int id) {
        this.id = id;
        this.dbLink = dbLink;
    }

    /**
     * creates the object record in the database
     */
    public void create() {
        //todo
    }

    /**
     * reads the object record in the database
     * @throws SQLException
     * @throws PreparedQueryException
     */
    public void read() throws SQLException, PreparedQueryException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ");
        query.append(this.getClass().getName());
        query.append("WHERE ID = ");
        PreparedQuery preparedQuery = dbLink.prepareQuery(query.toString());
        preparedQuery.executeQuery(id);
    }

    /**
     * updates the object record in the database
     */
    public void update() {
        //todo
    }

    /**
     * deletes the object record in the database
     * @throws SQLException
     * @throws PreparedQueryException
     */
    public void delete() throws SQLException, PreparedQueryException {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ");
        query.append(this.getClass().getName());
        query.append("WHERE ID = ");
        PreparedQuery preparedQuery = dbLink.prepareQuery(query.toString());
        preparedQuery.executeQuery(id);
    }

    /**
     * gets all the classe's fields and return their names, types and values for the ongoing instance
     * @return hashmap of the classe's fields
     * @throws DBObjectException if either a field cannot be found or it cannot be matched with a prepared statement type
     */
    private HashMap<String, Tuple<PreparedStatementTypes, Object>> getFieldsAndValues() throws DBObjectException {
        HashMap<String, Tuple<PreparedStatementTypes, Object>> fieldsAndValues = new HashMap<>();

        fieldsAndValues.put("id", new Tuple<>(PreparedStatementTypes.INT, id));

        for (Field f : this.getClass().getDeclaredFields()) {
            String getterName = f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
            try {
                Method getter = this.getClass().getDeclaredMethod(getterName);
                fieldsAndValues.put(f.getName(), new Tuple<>(convertJavaType(f.getType()), getter.invoke(this)));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new DBObjectUnreachableGetter(e);
            }
        }

        return fieldsAndValues;
    }

    /**
     * converts java types to prepared statement types to allow generic query generation
     * @param type of the field
     * @return prepared statement type matching the initial field
     * @throws DBObjectCannotDetectFieldType if there is not matching prepared statement type
     */
    private PreparedStatementTypes convertJavaType(Class type) throws DBObjectCannotDetectFieldType {
        switch (type.getName()) {
            case "Integer":
                return PreparedStatementTypes.INT;
            case "Float":
                return PreparedStatementTypes.FLOAT;
            case "String":
                return PreparedStatementTypes.STRING;
            case "Boolean":
                return PreparedStatementTypes.BOOLEAN;
            default:
                throw new DBObjectCannotDetectFieldType();
        }
    }

    public int getId() {
        return id;
    }

    public DBLink getDbLink() {
        return dbLink;
    }
}
