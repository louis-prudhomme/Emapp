package fr.efrei.se.emapp.api.model.business;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "CREDENTIAL")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Credential.checkCred", query = "SELECT c FROM Credential c WHERE c.login = :login AND c.pwd = :pwd")
})
public class Credential implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "LOGIN")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "PWD")
    private String pwd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "ISADMIN")
    private boolean isAdmin;

    /**
     * default constructor
     */
    public Credential() {
    }

    /**
     * constructor
     * @param id 
     */
    public Credential(Integer id) {
        this.id = id;
    }
    /**
     * constructor
     * @param login
     * @param pwd 
     */
    public Credential(String login, String pwd) {
        this.login = login;
        this.pwd = pwd;
    }
    /**
     * getter id
     * @return Integer : the id of the credential
     */
    public Integer getId() {
        return id;
    }
    /**
     * setter id
     * @param id of the credential
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * getter login credential
     * @return a string corresponding to the login
     */
    public String getLogin() {
        return login;
    }
    /**
     * login setter
     * @param login 
     */
    public void setLogin(String login) {
        this.login = login;
    }
    /**
     * Password getter
     * @return a string corresponding to the password
     */
    public String getPwd() {
        return pwd;
    }
    /**
     * password setter
     * @param pwd 
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    /**
     * 
     * @return an integer corresponding to the hash
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * 
     * @param object
     * @return a boolean 
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Credential)) {
            return false;
        }
        Credential other = (Credential) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @return a string
     */
    @Override
    public String toString() {
        return "fr.efrei.se.emapp.api.model.business.Credential[ id=" + id + " ]";
    }
    
}
