/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author lele
 */
@Entity
@NamedQuery(name = "getUserApiIndex", query = "SELECT c FROM UserApiIndexEntity C" )
public class UserApiIndexEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List < UserApiEntity > userApiIndexEntitys = new ArrayList<>();

    public UserApiIndexEntity() {
    }

    /**
     * Get index Users ( all users )
     * @return List<UserApiEntity>
     */
    public List<UserApiEntity> getUserApiIndexEntitys() {
        return userApiIndexEntitys;
    }

    /**
     * UnUsed
     * @param userApiIndexEntitys 
     */
    public void setUserApiIndexEntitys(List<UserApiEntity> userApiIndexEntitys) {
        this.userApiIndexEntitys = userApiIndexEntitys;
    }
    
    /**
     * Add User Api Index 
     * @param userApiIndexEntity 
     */
    public void addUserApiIndexEntitys( UserApiEntity userApiIndexEntity) {
        this.userApiIndexEntitys.add(userApiIndexEntity);
    }        
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserApiIndexEntity)) {
            return false;
        }
        UserApiIndexEntity other = (UserApiIndexEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alliancecontractmanager.db.entities.UserApiIndexEntity[ id=" + id + " ]";
    }
    
}
