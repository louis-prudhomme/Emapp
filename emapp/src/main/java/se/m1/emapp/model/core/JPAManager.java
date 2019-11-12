/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.m1.emapp.model.core;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import se.m1.emapp.model.business.Credential;
import se.m1.emapp.model.business.Employee;
import javax.ejb.EJBException;
@Stateless
public class JPAManager {
    /**
     * ArrayList that contains all the employees registered in the database
     */
    private ArrayList<Employee> all;

    @PersistenceContext
    private EntityManager em;

    private TypedQuery<Employee> queryEmployee;
    private TypedQuery<Credential> queryCredential;

    /**
     * allows the creation of an employee
     * @param e the employee to create
     * @throws EJBException
     */
    public void createEmployee(Employee e) throws EJBException {
        em.persist(e);
    }
    
    /**
     * allows employee deletion
     * @param e the employee to delete
     * @return a boolean that confirms if the employee is deleted
     * @throws EJBException
     */
    public boolean removeEmployee(Employee e) throws EJBException {
        if(read(e.getId()) == null) {
            return false;
        } else {
            if (!em.contains(e)) {
                e = em.merge(e);
            }
            em.remove(e);
            return true;
        }
    }
    
    /**
     * allows the modification of an employee
     * @param e the employee to modify
     * @return a boolean that confirms if the employee is modified
     * @throws EJBException
     */
    public boolean modifyEmployee(Employee e) throws EJBException{
        em.getEntityManagerFactory().getCache().evictAll();
        if(read(e.getId()) == null) {
            return false;
        } else {
            em.merge(e);
            return true;
        }
    }
    
    /**
     * Allows to get all the employee
     * @return an ArrayList with all the employees
     * @throws EJBException
     */
    public List<Employee> getAll() throws EJBException {
        queryEmployee = em.createNamedQuery("Employee.findAll", Employee.class);
        all = new ArrayList<>(queryEmployee.getResultList());
        return all;      
    }
    
    /**
     * get the details of an employee
     * @param id of the employee to read
     * @return The employee corresponding to the id
     * @throws EJBException
     */
       
    public Employee read(int id) throws EJBException {
        em.getEntityManagerFactory().getCache().evictAll();
        queryEmployee = em.createNamedQuery("Employee.findById", Employee.class);
        queryEmployee.setParameter("id", id);
        //we verify if the employee exists :
        if (queryEmployee.getResultList().isEmpty()) {
            return null;
        }
        return queryEmployee.getSingleResult();
    }
    
    /**
     * verify the credentials entered
     * @param c the credentials to be verified
     * @return a boolean : true if the credentials entered are valid, false if not
     * @throws EJBException
     */
    public boolean checkCredentials(Credential c) throws EJBException {
        queryCredential = em.createNamedQuery("Credential.checkcred", Credential.class);
        queryCredential.setParameter("login", c.getLogin());
        queryCredential.setParameter("pwd", c.getPwd());
        return !queryCredential.getResultList().isEmpty();
      
    }
}
