
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

    private TypedQuery<Employee> queryEmployee;
    private TypedQuery<Credential> queryCredential;
    
    public JPAManager(){

    }
    
    //Creation of an employee
    public void createEmployee(Employee e){ 
        EntityManagerFactory emf;
        EntityManager em;
        emf= Persistence.createEntityManagerFactory("my_persistence_unit") ;
        em= emf.createEntityManager();
        System.out.println("lskqjflfj%FJLSQJLFJLFJLfj"+e.getId());
        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit() ;
        em.close();
        emf.close();
    }
    //Employee delete
    public void removeEmployee(Employee e){
                EntityManagerFactory emf;
        EntityManager em;
        emf= Persistence.createEntityManagerFactory("my_persistence_unit") ;
        em= emf.createEntityManager();
        em.getTransaction().begin();
        if (!em.contains(e)){
            e = em.merge(e);
        }
        em.remove(e);
        em.getTransaction().commit() ;
                      em.close();
      emf.close();
    }
    //employee modification
    public void modifyEmployee(Employee e){  
                EntityManagerFactory emf;
        EntityManager em;
        emf= Persistence.createEntityManagerFactory("my_persistence_unit") ;
        em= emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(e);
        em.getTransaction().commit() ;
                      em.close();
      emf.close();
    }
    //get All employees
    public List<Employee> getAll(){ 
                EntityManagerFactory emf;
        EntityManager em;
        emf= Persistence.createEntityManagerFactory("my_persistence_unit") ;
        em= emf.createEntityManager();
        queryEmployee = em.createNamedQuery("Employee.findAll", Employee.class);
        all = new ArrayList<>(queryEmployee.getResultList());
                      em.close();
      emf.close();
        return all;      
    }
    //read an employee
    public Employee read(int id){ 
                EntityManagerFactory emf;
        EntityManager em;
        emf= Persistence.createEntityManagerFactory("my_persistence_unit") ;
        em= emf.createEntityManager();
        queryEmployee = em.createNamedQuery("Employee.findById", Employee.class);
        queryEmployee.setParameter("id",id); 
        Employee x = queryEmployee.getSingleResult();
              em.close();
      emf.close();
        return x; 
    }
    //verify the credentials entered
    public boolean checkCredentials(Credential e){ 
                EntityManagerFactory emf;
        EntityManager em;
        emf= Persistence.createEntityManagerFactory("my_persistence_unit") ;
        em= emf.createEntityManager();
      queryCredential = em.createNamedQuery("Credential.checkcred", Credential.class);
      queryCredential.setParameter("login",e.getLogin());
      queryCredential.setParameter("pwd",e.getPwd());
      boolean x = !queryCredential.getResultList().isEmpty();
      em.close();
      emf.close();
      return x;
    }
    public void contextDestroyed() {

}

    
}
