package fr.efrei.se.emapp.api.model.core;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import fr.efrei.se.emapp.api.model.business.Credential;
import fr.efrei.se.emapp.api.model.business.Employee;
import fr.efrei.se.emapp.common.model.exception.WrongParameterException;
import fr.efrei.se.emapp.common.model.exception.EmptyResultException;

@Stateless
public class JPAManager {

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
     * @throws EJBException database exception
     * @throws WrongParameterException
     * @throws EmptyResultException
     */
    public boolean removeEmployee(Employee e) throws EJBException, EmptyResultException, WrongParameterException {
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
     * @throws EJBException database exception
     * @throws WrongParameterException
     * @throws EmptyResultException
     */
    public boolean modifyEmployee(Employee e) throws EJBException, EmptyResultException, WrongParameterException {
        em.getEntityManagerFactory().getCache().evictAll();
        if(read(e.getId()) == null) {
            return false;
        }else{
            em.merge(e);
            return true;
        }
    }
    
    /**
     * Allows to get all the employee
     * @return an ArrayList with all the employees
     * @throws EJBException database exception
     * @throws EmptyResultException
     */
    public List<Employee> getAll() throws EJBException, EmptyResultException {
        queryEmployee = em.createNamedQuery("Employee.findAll", Employee.class);
        all = new ArrayList<>(queryEmployee.getResultList());

        if(all.isEmpty())
            throw new EmptyResultException();

        return all;      
    }
    
    /**
     * get the details of an employee
     * @param id of the employee to read
     * @return The employee corresponding to the id
     * @throws EJBException database exception
     * @throws WrongParameterException
     * @throws EmptyResultException
     */
    public Employee read(int id) throws EmptyResultException, WrongParameterException, EJBException {
        if (id == 0)
            throw new WrongParameterException();

        em.getEntityManagerFactory().getCache().evictAll();
        queryEmployee = em.createNamedQuery("Employee.findById", Employee.class);
        queryEmployee.setParameter("id", id);

        if (queryEmployee.getResultList().isEmpty())
            throw new EmptyResultException();

        return queryEmployee.getSingleResult();
    }

    /**
     * verify the credentials entered
     * @param c the credentials to be verified
     * @return the corresponding credential if it exists
     * @throws EJBException database exception
     * @throws WrongParameterException
     * @throws EmptyResultException
     */
    public Credential checkCredentials(Credential c) throws WrongParameterException, EmptyResultException, EJBException {
        queryCredential = em.createNamedQuery("Credential.checkCred", Credential.class);

        if(c.getLogin() == null || c.getPwd() == null)
            throw new WrongParameterException();

        queryCredential.setParameter("login", c.getLogin());
        queryCredential.setParameter("pwd", c.getPwd());

        if(queryCredential.getResultList().isEmpty())
            throw new EmptyResultException();

        return queryCredential.getResultList().get(0);
    }
}