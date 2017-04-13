/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.manager.ManagerMicrimDB;

import alliancecontractmanager.db.controllers.UserApiEntityJpaController;
import alliancecontractmanager.db.controllers.UserApiIndexEntityJpaController;
import alliancecontractmanager.db.entities.UserApiEntity;
import alliancecontractmanager.db.entities.UserApiIndexEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author lele
 */
public class ManagerSQLUser {
    private static ManagerSQLUser instance = null;
    
    private EntityManager entityManagerEM = 
     Persistence.createEntityManagerFactory("AllianceContractManagerPU").createEntityManager();

    // Si può migliorare
    private EntityManagerFactory entityManagerFactoryEMF =
     Persistence.createEntityManagerFactory("AllianceContractManagerPU");    
    
    private UserApiEntityJpaController 
     userKeyIDJpaController = new UserApiEntityJpaController
     (Persistence.createEntityManagerFactory("AllianceContractManagerPU"));
    
    public static ManagerSQLUser getInstance(){
        if ( instance == null ){
            instance = new ManagerSQLUser();
        }
        return instance;
    }

    /**
     * Get User Api Index ( all list of users )
     * @return List < UserApiIndexEntity >
     */
    public List < UserApiIndexEntity > getUserApiIndexEntity(){
        try {
            EntityManager getUserApiIndexEntityEM = 
             Persistence.createEntityManagerFactory("AllianceContractManagerPU").createEntityManager();

            TypedQuery < UserApiIndexEntity > getUserApiIndexEntityTQ =
             getUserApiIndexEntityEM.createNamedQuery("getUserApiIndex", UserApiIndexEntity.class);

            return getUserApiIndexEntityTQ.getResultList();            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Add User Api Inde xEntity
     * @param userApiIndexEntity 
     */
    public void addUserApiIndexEntity(UserApiIndexEntity userApiIndexEntity ){
        try {
            UserApiIndexEntityJpaController userApiIndexEntityJpaController = new 
             UserApiIndexEntityJpaController(Persistence.createEntityManagerFactory("AllianceContractManagerPU"));

            userApiIndexEntityJpaController.create(userApiIndexEntity);            
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    /**
     * Update User Api Index 
     * @param UserApiIndexEntity userApiIndexEntity 
     */
    public void updateUserApiEntity(UserApiEntity userApiEntity){
        try {
            UserApiEntityJpaController userApiEntityJpaController = 
             new UserApiEntityJpaController(entityManagerFactoryEMF);

            userApiEntityJpaController.edit(userApiEntity);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}
