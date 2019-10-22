/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.m1.emapp.model.business;
import se.m1.emapp.model.core.DBLink;
import se.m1.emapp.model.core.DBObject;
/**
 *
 * @author melaniemarques
 */
public class Credential extends DBObject {
    private String login;
    private String pwd;
    
    public Credential(DBLink dbLink, Integer id) {
        super(dbLink, id);
    }
    
    public Credential(DBLink dbLink, Integer id, String login, String pwd){
        super(dbLink, id);
        this.login = login;
        this.pwd = pwd;
    }
    
    public Credential(DBLink dbLink){
        super(dbLink);
        this.login = login;
        this.pwd = pwd;
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
}
