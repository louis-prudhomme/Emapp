package fr.efrei.se.emapp.api.model.core;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import fr.efrei.se.emapp.api.model.business.Credential;
import fr.efrei.se.emapp.api.model.business.Employee;
import fr.efrei.se.emapp.api.model.exception.EmptyParameterException;
import fr.efrei.se.emapp.api.model.exception.EmptyResultException;

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
     */
    public void createEmployee(Employee e){ 
        em.persist(e);
    }
    
    /**
     * allows employee deletion
     * @param e the employee to delete
     */
    public void removeEmployee(Employee e){
        if (!em.contains(e)){
            e = em.merge(e);
        }
        em.remove(e);
    }
    
    /**
     * allows the modification of an employee
     * @param e the employee to modify
     */
    public void modifyEmployee(Employee e) {
        em.merge(e);
    }
    
    /**
     * Allows to get all the employee
     * @return an ArrayList with all the employees
     */
    public List<Employee> getAll() throws EmptyResultException {
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
     */
    public Employee read(int id) throws EmptyResultException, EmptyParameterException {
        if(id == 0)
            throw new EmptyParameterException();

        queryEmployee = em.createNamedQuery("Employee.findById", Employee.class);
        queryEmployee.setParameter("id", id);

        if(queryEmployee.getResultList().isEmpty())
            throw new EmptyResultException();

        return queryEmployee.getSingleResult();
    }
    
    /**
     * verify the credentials entered
     * @param c the credentials to be verified
     * @return the corresponding creedential if it exists
     */
    public Credential checkCredentials(Credential c) throws EmptyParameterException, EmptyResultException {
        queryCredential = em.createNamedQuery("Credential.checkCred", Credential.class);

        if(c.getLogin() == null || c.getPwd() == null)
            throw new EmptyParameterException();

        queryCredential.setParameter("login", c.getLogin());
        queryCredential.setParameter("pwd", c.getPwd());

        if(queryCredential.getResultList().isEmpty())
            throw new EmptyResultException();

        return queryCredential.getResultList().get(0);
    }
}
