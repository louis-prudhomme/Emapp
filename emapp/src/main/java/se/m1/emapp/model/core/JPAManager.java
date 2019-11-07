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

/**
 *
 * @author melaniemarques
 */
@Stateless
public class JPAManager {

    private ArrayList<Employee> all;
    
    @PersistenceContext
    private EntityManager em;
    private TypedQuery<Employee> queryEmployee;
    private TypedQuery<Credential> queryCredential;
  
    //Creation of an employee
    public void createEmployee(Employee e){ 
        em.persist(e);


    }
    //Employee delete
    public void removeEmployee(Employee e){
        if (!em.contains(e)){
            e = em.merge(e);
        }
        em.remove(e);

    }
    //employee modification
    public void modifyEmployee(Employee e){  

        em.merge(e);


    }
    //get All employees
    public List<Employee> getAll(){ 

        queryEmployee = em.createNamedQuery("Employee.findAll", Employee.class);
        all = new ArrayList<>(queryEmployee.getResultList());

        return all;      
    }
    //read an employee
    public Employee read(int id){ 

        queryEmployee = em.createNamedQuery("Employee.findById", Employee.class);
        queryEmployee.setParameter("id",id); 
        Employee x = queryEmployee.getSingleResult();

        return x; 
    }
    //verify the credentials entered
    public boolean checkCredentials(Credential e){ 
      queryCredential = em.createNamedQuery("Credential.checkcred", Credential.class);
      queryCredential.setParameter("login",e.getLogin());
      queryCredential.setParameter("pwd",e.getPwd());
      boolean x = !queryCredential.getResultList().isEmpty();

      return x;
    }
}
