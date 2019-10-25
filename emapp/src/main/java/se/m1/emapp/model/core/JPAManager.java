
package se.m1.emapp.model.core;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import se.m1.emapp.model.business.Credential;
import se.m1.emapp.model.business.Employee;

public class JPAManager 
{
    private ArrayList<Employee> all;
    private EntityManagerFactory emf;
    private EntityManager em;
    private TypedQuery<Employee> queryEmployee;
    private TypedQuery<Credential> queryCredential;
    
    public JPAManager(){
        emf= Persistence.createEntityManagerFactory("my_persistence_unit") ;
        em= emf.createEntityManager();
    }
    
    //Creation of an employee
    public void createEmployee(Employee e){             
        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit() ;
    }
    //Employee delete
    public void removeEmployee(Employee e){    
        em.getTransaction().begin();
        if (!em.contains(e)){
            e = em.merge(e);
        }
        em.remove(e);
        em.getTransaction().commit() ;
    }
    //employee modification
    public void modifyEmployee(Employee e){     
        em.getTransaction().begin();
        em.merge(e);
        em.getTransaction().commit() ;
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
        return queryEmployee.getSingleResult(); 
    }
    //verify the credentials entered
    public boolean checkCredentials(Credential e){    
      queryCredential = em.createNamedQuery("Credential.checkcred", Credential.class);
      queryCredential.setParameter("login",e.getLogin());
      queryCredential.setParameter("pwd",e.getPwd());
      return !queryCredential.getResultList().isEmpty();
    }
    

    
}
